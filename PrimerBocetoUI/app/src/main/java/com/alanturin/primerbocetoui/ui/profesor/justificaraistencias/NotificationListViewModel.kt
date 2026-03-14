package com.alanturin.primerbocetoui.ui.profesor.justificaraistencias


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val assistanceRepository: AssistanceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        cargarNotificaciones()
    }

    fun cargarNotificaciones() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = assistanceRepository.getPendingJustifications()

            result.onSuccess { lista ->
                if (lista.isEmpty()) {
                    _uiState.value = UiState.Empty
                } else {
                    _uiState.value = UiState.Success(lista)
                }
            }.onFailure {
                _uiState.value = UiState.Error("Error al cargar notificaciones")
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val notificaciones: List<AssistanceStudentItemRemote>) : UiState()
        data class Error(val message: String) : UiState()
    }
}