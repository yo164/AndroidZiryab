package com.alanturin.primerbocetoui.ui.profesor.justificaraistencias


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.data.repository.notifications.NotificationsRepository
import com.alanturin.primerbocetoui.domain.model.AssistanceItem
import com.alanturin.primerbocetoui.domain.model.NotificationItem
import com.alanturin.primerbocetoui.ui.session.JustificacionSessionService
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationListViewModel @Inject constructor(
    private val justificacionSessionService: JustificacionSessionService,
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        cargarNotificaciones()
    }

    fun cargarNotificaciones() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading



            val result = notificationsRepository.getAllNotifications()

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

    fun seleccionarNotificacion(notificacion: NotificationItem) {
        justificacionSessionService.set(
            uri = notificacion.uri ?: "",
            idAssistance = notificacion.idAssistance
        )
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val notificaciones: List<NotificationItem>) : UiState()
        data class Error(val message: String) : UiState()
    }
}