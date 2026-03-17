package com.alanturin.primerbocetoui.ui.profesor.listaAlumnos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceItemRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.EnrollmentItemRemote
import com.alanturin.primerbocetoui.data.repository.EnrollmentRepository
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.domain.model.AssistanceItem

import com.alanturin.primerbocetoui.ui.session.AssignmentSessionService
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlumnoListViewModel @Inject constructor(
    private val repository: EnrollmentRepository,
    private val assignmentSessionService: AssignmentSessionService,
    private val assistanceRepository: AssistanceRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _asistencias = MutableStateFlow<Map<Int, AssistanceStatus>>(emptyMap())
    val asistencias: StateFlow<Map<Int, AssistanceStatus>> = _asistencias

    fun actualizarAsistencia(enrollmentId: Int, status: AssistanceStatus) {
        _asistencias.value = _asistencias.value + (enrollmentId to status)
    }
    fun cargarAlumnos() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val idSubject = assignmentSessionService.currentSubjectId.value
            val idGroup = assignmentSessionService.currentGroupId.value

            if (idSubject != null && idGroup != null) {
                val result = repository.getEnrollmentsByFilters(idSubject, idGroup, "2024-2025")

                result.onSuccess { lista ->
                    if (lista.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(lista)
                        comprobarAsistenciasExistentes()
                    }
                }.onFailure {
                    _uiState.value = UiState.Error("Error al cargar alumnos")
                }
            } else {
                _uiState.value = UiState.Error("No hay asignatura seleccionada")
            }
        }
    }

    fun enviarAsistencias() {
        android.util.Log.d("ZIRYAB", "Preparando datos para enviar asistencias...")
        val idSession = assignmentSessionService.currentClassSession.value
        val idUser = sessionViewModel.userId.value ?: 0
        if (idSession == null || idSession == 0) {
            android.util.Log.d("ZIRYAB", "No hay sesión de clase activa")
            return
        }
        android.util.Log.d("ZIRYAB", "id de la class SEssion: $idSession")

        viewModelScope.launch {
            val getResult = assistanceRepository.getAssistancesBySessionId(idSession)

            getResult.onSuccess { listaExistente ->
                if (listaExistente.isEmpty()) {
                    enviarBulk(idSession)
                } else {
                    patchAsistencias(listaExistente)
                }
            }.onFailure {
                android.util.Log.d("ZIRYAB", "Error al verificar asistencias: ${it.message}")
            }
        }
    }

    private fun enviarBulk(idSession: Int) {
        val state = _uiState.value
        var listaAsistencias: List<AssistanceItemRequestRemote> = emptyList()

        if (state is UiState.Success) {
            listaAsistencias = state.alumnos.map { enrollment ->
                val status = _asistencias.value[enrollment.id] ?: AssistanceStatus.PRESENT
                android.util.Log.d("ZIRYAB", "idSession: $idSession, idEnrollment: ${enrollment.id}, status: $status")
                AssistanceItemRequestRemote(
                    idSession = idSession,
                    idStudentEnrollment = enrollment.id,
                    status = status.name
                )
            }
        }

        viewModelScope.launch {
            val result = assistanceRepository.createBulk(AssistanceBulkRequestRemote(assistances = listaAsistencias))
            result.onSuccess {
                android.util.Log.d("ZIRYAB", "Asistencias enviadas: ${it.count}")
            }.onFailure {
                android.util.Log.d("ZIRYAB", "Error al enviar asistencias: ${it.message}")
            }
        }
    }

    private fun comprobarAsistenciasExistentes() {
        val idSession = assignmentSessionService.currentClassSession.value ?: return

        viewModelScope.launch {
            val getResult = assistanceRepository.getAssistancesBySessionId(idSession)

            getResult.onSuccess { listaExistente ->
                if (listaExistente.isNotEmpty()) {
                    listaExistente.forEach { asistencia ->
                        _asistencias.value = (_asistencias.value +
                                (asistencia.idStudentEnrollment to AssistanceStatus.valueOf(asistencia.status)))
                    }
                }
            }.onFailure {
                android.util.Log.d("ZIRYAB", "Error al comprobar asistencias: ${it.message}")
            }
        }
    }

    private fun patchAsistencias(listaExistente: List<AssistanceItem>) {
        viewModelScope.launch {
            listaExistente.forEach { asistenciaExistente ->
                val nuevoStatus = _asistencias.value[asistenciaExistente.idStudentEnrollment]
                    ?: AssistanceStatus.PRESENT
                if (asistenciaExistente.status != nuevoStatus.name) {
                    android.util.Log.d("ZIRYAB", "Actualizando id: ${asistenciaExistente.id} a status: ${nuevoStatus.name}")
                    assistanceRepository.patchAssistancebyId(
                        id = asistenciaExistente.id,
                        status = nuevoStatus.name
                    )
                }
            }
        }
    }
    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val alumnos: List<EnrollmentItemRemote>) : UiState()
        data class Error(val message: String) : UiState()
    }
}