package com.alanturin.primerbocetoui.ui.horario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.domain.model.HorarioItem
import com.alanturin.primerbocetoui.data.repository.WeekScheduleRepository
import com.alanturin.primerbocetoui.data.repository.studentweekschedule.StudentWeekScheduleRepository
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HorarioListViewModel @Inject constructor(
    private val weekScheduleRepository: WeekScheduleRepository,
    private val studentScheduleRepository: StudentWeekScheduleRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun cargarHorario() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val userId = sessionViewModel.userId.value
            val userRole = sessionViewModel.userRole.value

            if (userId == null || userRole == null) {
                _uiState.value = UiState.Error("No hay sesión activa")
                return@launch
            }

            val result = when (userRole) {
                "TEACHER" -> weekScheduleRepository.getWeekScheduleByTeacher(userId.toLong())
                "STUDENT" -> studentScheduleRepository.getWeekScheduleByStudent(userId.toLong())
                else -> Result.failure(RuntimeException("Rol desconocido"))
            }

            result.onSuccess { items ->
                _uiState.value = if (items.isEmpty()) UiState.Empty else UiState.Success(items)
            }.onFailure {
                _uiState.value = UiState.Error("Error al cargar horario")
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val horarios: List<HorarioItem>) : UiState()
        data class Error(val message: String) : UiState()
    }
}