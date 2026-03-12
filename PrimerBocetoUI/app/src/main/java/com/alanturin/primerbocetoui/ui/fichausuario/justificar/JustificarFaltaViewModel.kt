package com.alanturin.primerbocetoui.ui.fichausuario.justificar

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JustificarFaltaViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var selectedFileUri: Uri? = null

    fun onFileSelected(uri: Uri) {
        selectedFileUri = uri
        android.util.Log.d("ZIRYAB", "Archivo del dispositivo: $uri")
        _uiState.value = UiState.FileSelected(uri)
    }

    fun onPhotoTaken(uri: Uri) {
        selectedFileUri = uri
        android.util.Log.d("ZIRYAB", "Foto de la cámara: $uri")
        _uiState.value = UiState.FileSelected(uri)
    }

    fun enviarJustificacion(subject: String, date: String) {
        val uri = selectedFileUri
        if (uri == null) {
            android.util.Log.e("ZIRYAB", "No hay archivo para enviar")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Sending

            android.util.Log.d("ZIRYAB", "Enviando justificante para $subject del día $date. Archivo: $uri")
            kotlinx.coroutines.delay(2000)
            _uiState.value = UiState.Success
        }
    }
    sealed class UiState {
        object Idle : UiState()
        data class FileSelected(val uri: Uri) : UiState()
        object Sending : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}