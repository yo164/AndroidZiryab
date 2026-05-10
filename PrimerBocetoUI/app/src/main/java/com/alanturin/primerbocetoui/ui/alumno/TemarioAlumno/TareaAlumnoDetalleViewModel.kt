package com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import com.alanturin.primerbocetoui.data.repository.studenttask.StudentTaskRepository
import com.alanturin.primerbocetoui.data.repository.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TareaAlumnoDetalleViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val studentTaskRepository: StudentTaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TareaAlumnoDetalleUiState>(TareaAlumnoDetalleUiState.Loading)
    val uiState: StateFlow<TareaAlumnoDetalleUiState> = _uiState.asStateFlow()

    fun cargar(taskId: Int, enrollmentId: Int) {
        viewModelScope.launch {
            _uiState.value = TareaAlumnoDetalleUiState.Loading
            taskRepository.getTaskById(taskId).onSuccess { tarea ->
                studentTaskRepository.getSubmissionsByTask(taskId).onSuccess { entregas ->
                    val previa = entregas.firstOrNull { it.idStudentEnrollment == enrollmentId }
                    _uiState.value = TareaAlumnoDetalleUiState.Success(tarea, previa)
                }.onFailure {
                    _uiState.value = TareaAlumnoDetalleUiState.Success(tarea, null)
                }
            }.onFailure { e ->
                _uiState.value = TareaAlumnoDetalleUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun entregar(taskId: Int, enrollmentId: Int) {
        val estado = _uiState.value
        if (estado !is TareaAlumnoDetalleUiState.Success) return
        val tarea = estado.tarea
        viewModelScope.launch {
            _uiState.value = TareaAlumnoDetalleUiState.Enviando(tarea)
            val request = SubmitTaskRequestRemote(
                idTask = taskId,
                idStudentEnrollment = enrollmentId
            )
            studentTaskRepository.submitTask(request).onSuccess {
                _uiState.value = TareaAlumnoDetalleUiState.Enviada(tarea)
            }.onFailure { e ->
                _uiState.value = TareaAlumnoDetalleUiState.Error(e.message ?: "Error al entregar")
            }
        }
    }
}

sealed class TareaAlumnoDetalleUiState {
    object Loading : TareaAlumnoDetalleUiState()
    data class Error(val mensaje: String) : TareaAlumnoDetalleUiState()
    data class Success(
        val tarea: TaskItemRemote,
        val entregaPrevia: StudentTaskItemRemote?
    ) : TareaAlumnoDetalleUiState()
    data class Enviando(val tarea: TaskItemRemote) : TareaAlumnoDetalleUiState()
    data class Enviada(val tarea: TaskItemRemote) : TareaAlumnoDetalleUiState()
}
