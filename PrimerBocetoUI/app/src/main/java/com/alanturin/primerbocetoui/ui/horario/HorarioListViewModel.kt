package com.alanturin.primerbocetoui.ui.horario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote
import com.alanturin.primerbocetoui.data.repository.WeekScheduleRepository
import com.alanturin.primerbocetoui.ui.session.AssignmentSessionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HorarioViewModel @Inject constructor(
    private val repository: WeekScheduleRepository,
    private val assignmentSession: AssignmentSessionService
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun cargarHorario() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val idAssignment = assignmentSession.currentAssignmentId.value

            if (idAssignment != null) {
                val result = repository.getWeekScheduleByAssignment(idAssignment.toLong())

                result.onSuccess { lista ->
                    if (lista.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(lista)
                    }
                }.onFailure {
                    _uiState.value = UiState.Error("Error al cargar horario")
                }
            } else {
                _uiState.value = UiState.Error("No hay asignatura seleccionada")
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val horarios: List<WeekScheduleItemRemote>) : UiState()
        data class Error(val message: String) : UiState()
    }
}