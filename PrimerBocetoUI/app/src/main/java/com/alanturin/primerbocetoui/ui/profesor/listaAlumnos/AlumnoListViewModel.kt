package com.alanturin.primerbocetoui.ui.profesor.listaAlumnos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.EnrollmentItemRemote
import com.alanturin.primerbocetoui.data.repository.EnrollmentRepository

import com.alanturin.primerbocetoui.ui.session.AssignmentSessionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlumnoListViewModel @Inject constructor(
    private val repository: EnrollmentRepository,
    private val assignmentSessionService: AssignmentSessionService
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

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

        val state = _uiState.value

        if (state is UiState.Success) {
            state.alumnos.forEach { enrollment ->
                // idStudentEnrollment - lo tenemos, es el id del enrollment
                android.util.Log.d("ZIRYAB", "idStudentEnrollment: ${enrollment.id}")

                // idSession - necesitamos el id de la sesión de clase de hoy, aun no lo tenemos
                // android.util.Log.d("ZIRYAB", "idSession: ???")

                // status - F, R o A que marca el profesor en la tarjeta, aun no lo recogemos
                // android.util.Log.d("ZIRYAB", "status: ???")
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