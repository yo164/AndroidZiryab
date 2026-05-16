package com.alanturin.primerbocetoui.ui.profesor.calificarEntrega

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.GradeSubmissionRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.repository.studenttask.StudentTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalificarEntregaViewModel @Inject constructor(
    private val studentTaskRepository: StudentTaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val submissionId: Int = checkNotNull(savedStateHandle["submissionId"])
    private val taskId: Int = checkNotNull(savedStateHandle["taskId"])

    private val _uiState = MutableStateFlow<CalificarEntregaUiState>(CalificarEntregaUiState.Loading)
    val uiState: StateFlow<CalificarEntregaUiState> = _uiState.asStateFlow()

    init {
        cargar()
    }

    fun cargar() {
        viewModelScope.launch {
            _uiState.value = CalificarEntregaUiState.Loading
            studentTaskRepository.getSubmissionsByTask(taskId).onSuccess { lista ->
                val entrega = lista.firstOrNull { it.id == submissionId }
                if (entrega != null) {
                    _uiState.value = CalificarEntregaUiState.Listo(entrega)
                } else {
                    _uiState.value = CalificarEntregaUiState.Error("Entrega no encontrada")
                }
            }.onFailure { e ->
                _uiState.value = CalificarEntregaUiState.Error(e.message ?: "Error al cargar")
            }
        }
    }

    fun calificar(score: Double, feedback: String) {
        if (score < 0.0 || score > 10.0) {
            _uiState.value = CalificarEntregaUiState.Error("La nota debe estar entre 0 y 10")
            return
        }
        viewModelScope.launch {
            _uiState.value = CalificarEntregaUiState.Guardando
            val request = GradeSubmissionRequestRemote(
                score = score,
                feedback = feedback.takeIf { it.isNotBlank() }
            )
            studentTaskRepository.gradeSubmission(submissionId, request).onSuccess {
                _uiState.value = CalificarEntregaUiState.Guardado
            }.onFailure { e ->
                _uiState.value = CalificarEntregaUiState.Error(e.message ?: "Error al calificar")
            }
        }
    }
}


sealed class CalificarEntregaUiState {
    object Loading : CalificarEntregaUiState()
    data class Listo(val entrega: StudentTaskItemRemote) : CalificarEntregaUiState()
    data class Error(val mensaje: String) : CalificarEntregaUiState()
    object Guardando : CalificarEntregaUiState()
    object Guardado : CalificarEntregaUiState()
}