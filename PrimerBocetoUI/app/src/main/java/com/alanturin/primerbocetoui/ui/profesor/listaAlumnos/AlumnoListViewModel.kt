package com.alanturin.primerbocetoui.ui.profesor.listaAlumnos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceItemRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.EnrollmentItemRemote
import com.alanturin.primerbocetoui.data.repository.EnrollmentRepository
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository

import com.alanturin.primerbocetoui.ui.session.AssignmentSessionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlumnoListViewModel @Inject constructor(
    private val repository: EnrollmentRepository,
    private val assignmentSessionService: AssignmentSessionService,
    private val assistanceRepository: AssistanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _asistencias = MutableStateFlow<Map<Int, AssistanceStatus>>(emptyMap())

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

        if (idSession == null || idSession == 0) {
            android.util.Log.d("ZIRYAB", "No hay sesión de clase activa")
            return
        }
        android.util.Log.d("ZIRYAB", "id de la class SEssion: $idSession")

        val state = _uiState.value

        var listaAsistencias : List<AssistanceItemRequestRemote> = emptyList()
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
            val request = AssistanceBulkRequestRemote(assistances = listaAsistencias)
            val result = assistanceRepository.createBulk(request)

            result.onSuccess {
                android.util.Log.d("ZIRYAB", "Asistencias enviadas: ${it.count}")
            }.onFailure {
                android.util.Log.d("ZIRYAB", "Error al enviar asistencias: ${it.message}")
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