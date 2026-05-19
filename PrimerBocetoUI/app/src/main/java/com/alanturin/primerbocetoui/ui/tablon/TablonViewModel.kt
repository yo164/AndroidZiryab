package com.alanturin.primerbocetoui.ui.tablon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.repository.announcement.AnnouncementRepository
import com.alanturin.primerbocetoui.domain.model.Announcement
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TablonViewModel @Inject constructor(
    private val repository: AnnouncementRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _creationState = MutableStateFlow<CreationState>(CreationState.Idle)
    val creationState: StateFlow<CreationState> = _creationState.asStateFlow()

    val userRole: StateFlow<String?> = sessionViewModel.userRole

    init {
        cargarAnuncios()
    }

    fun cargarAnuncios() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.getAnnouncements()
                .onSuccess { list ->
                    if (list.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(list)
                    }
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(error.message ?: "Error desconocido al obtener anuncios")
                }
        }
    }

    fun crearAnuncio(title: String, body: String) {
        if (title.isBlank() || body.isBlank()) {
            _creationState.value = CreationState.Error("El título y el cuerpo no pueden estar vacíos")
            return
        }

        viewModelScope.launch {
            _creationState.value = CreationState.Loading
            repository.createAnnouncement(title, body)
                .onSuccess {
                    _creationState.value = CreationState.Success
                    cargarAnuncios() // Recargar lista al crear con éxito
                }
                .onFailure { error ->
                    _creationState.value = CreationState.Error(error.message ?: "Error al publicar anuncio")
                }
        }
    }

    fun resetCreationState() {
        _creationState.value = CreationState.Idle
    }

    fun canPublish(): Boolean {
        val role = userRole.value
        return role == "TEACHER" || role == "ADMIN"
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val announcements: List<Announcement>) : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class CreationState {
        object Idle : CreationState()
        object Loading : CreationState()
        object Success : CreationState()
        data class Error(val message: String) : CreationState()
    }
}
