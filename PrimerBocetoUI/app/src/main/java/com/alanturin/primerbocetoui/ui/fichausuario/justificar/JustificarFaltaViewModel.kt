package com.alanturin.primerbocetoui.ui.fichausuario.justificar

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class JustificarFaltaViewModel @Inject constructor(
    private val assistanceRepository: AssistanceRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var selectedFileUri: Uri? = null

    fun onFileSelected(uri: Uri) {
        selectedFileUri = uri
        android.util.Log.d("ZIRYAB", "Archivo del dispositivo: $uri")
        _uiState.value = UiState.FileSelected(uri)
    }

    fun onPhotoTaken(uri: Uri) {
        val picturesDir = File(context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES), "justificantes")
        if (!picturesDir.exists()) picturesDir.mkdirs()

        val file = File(picturesDir, "${System.currentTimeMillis()}.jpg")

        File(uri.path!!).copyTo(file, overwrite = true)

        selectedFileUri = Uri.fromFile(file)
        android.util.Log.d("ZIRYAB", "Foto de la cámara: $uri")
        _uiState.value = UiState.FileSelected(selectedFileUri!!)
    }

    fun enviarJustificacion(id: Int,subject: String, date: String) {
        val uri = selectedFileUri
        if (uri == null) {
            android.util.Log.e("ZIRYAB", "No hay archivo para enviar")
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Sending

            android.util.Log.d("ZIRYAB", "Enviando justificante para la asistencia $id, $subject del día $date. Archivo: $uri")
            assistanceRepository.justifyRequest(id, uri.toString())
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