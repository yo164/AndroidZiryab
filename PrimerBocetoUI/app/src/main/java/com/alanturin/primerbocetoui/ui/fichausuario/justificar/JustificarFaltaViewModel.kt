package com.alanturin.primerbocetoui.ui.fichausuario.justificar



import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JustificarFaltaViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onFileSelected(uri: Uri) {
        android.util.Log.d("ZIRYAB", "Fichero seleccionado: $uri")
        _uiState.value = UiState.FileSelected(uri)
    }

    fun onPhotoTaken(uri: Uri) {
        android.util.Log.d("ZIRYAB", "Foto tomada: $uri")
        _uiState.value = UiState.FileSelected(uri)
    }

    fun enviarJustificacion() {
        // TODO: POST al backend
        android.util.Log.d("ZIRYAB", "Enviando justificación...")
    }

    sealed class UiState {
        object Idle : UiState()
        data class FileSelected(val uri: Uri) : UiState()
        object Sending : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}