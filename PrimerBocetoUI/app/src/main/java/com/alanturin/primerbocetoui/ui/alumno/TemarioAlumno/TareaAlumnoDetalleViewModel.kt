package com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import com.alanturin.primerbocetoui.data.repository.studenttask.StudentTaskRepository
import com.alanturin.primerbocetoui.data.repository.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TareaAlumnoDetalleViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val studentTaskRepository: StudentTaskRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<TareaAlumnoDetalleUiState>(TareaAlumnoDetalleUiState.Loading)
    val uiState: StateFlow<TareaAlumnoDetalleUiState> = _uiState.asStateFlow()

    fun cargar(taskId: Int, enrollmentId: Int) {
        viewModelScope.launch {
            _uiState.value = TareaAlumnoDetalleUiState.Loading
            android.util.Log.d("ZIRYAB", "TareaAlumnoDetalleViewModel.cargar: taskId=$taskId, enrollmentId=$enrollmentId")
            taskRepository.getTaskById(taskId).onSuccess { tarea ->
                android.util.Log.d("ZIRYAB", "taskRepository.getTaskById success: $tarea")
                studentTaskRepository.getByStudentEnrollment(enrollmentId).onSuccess { entregas ->
                    android.util.Log.d("ZIRYAB", "studentTaskRepository.getByStudentEnrollment success: count=${entregas.size}, items=$entregas")
                    val previa = entregas.firstOrNull { it.idTask == taskId }
                    android.util.Log.d("ZIRYAB", "Encontrada entrega previa: $previa")
                    _uiState.value = TareaAlumnoDetalleUiState.Success(tarea, previa)
                }.onFailure { e ->
                    android.util.Log.e("ZIRYAB", "studentTaskRepository.getByStudentEnrollment failed", e)
                    _uiState.value = TareaAlumnoDetalleUiState.Error("Error al cargar el estado de la entrega: ${e.message}")
                }
            }.onFailure { e ->
                android.util.Log.e("ZIRYAB", "taskRepository.getTaskById failed", e)
                _uiState.value = TareaAlumnoDetalleUiState.Error(e.message ?: "Error desconocido al cargar la tarea")
            }
        }
    }

    private fun getMultipartBodyPart(uri: Uri): MultipartBody.Part? {
        return try {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val tempFile = File.createTempFile("upload", ".tmp", context.cacheDir)
            tempFile.deleteOnExit()
            tempFile.outputStream().use { output ->
                inputStream.use { input ->
                    input.copyTo(output)
                }
            }
            val mimeType = contentResolver.getType(uri) ?: "application/octet-stream"
            val requestFile = RequestBody.create(
                mimeType.toMediaTypeOrNull(),
                tempFile
            )
            MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun entregarConUrl(taskId: Int, enrollmentId: Int, url: String) {
        val estado = _uiState.value
        if (estado !is TareaAlumnoDetalleUiState.Success) return
        val tarea = estado.tarea
        val previa = estado.entregaPrevia
        if (previa == null) {
            _uiState.value = TareaAlumnoDetalleUiState.Error("No existe registro de entrega previa")
            return
        }
        viewModelScope.launch {
            _uiState.value = TareaAlumnoDetalleUiState.Enviando(tarea)
            val request = SubmitTaskRequestRemote(
                attachmentUrl = url
            )
            studentTaskRepository.submitTask(previa.id, request).onSuccess { entregada ->
                _uiState.value = TareaAlumnoDetalleUiState.Success(tarea, entregada)
            }.onFailure { e ->
                _uiState.value = TareaAlumnoDetalleUiState.Error(e.message ?: "Error al entregar enlace")
            }
        }
    }

    fun entregarConArchivo(taskId: Int, enrollmentId: Int, uri: Uri) {
        val estado = _uiState.value
        if (estado !is TareaAlumnoDetalleUiState.Success) return
        val tarea = estado.tarea
        val previa = estado.entregaPrevia
        if (previa == null) {
            _uiState.value = TareaAlumnoDetalleUiState.Error("No existe registro de entrega previa")
            return
        }
        viewModelScope.launch {
            _uiState.value = TareaAlumnoDetalleUiState.Enviando(tarea)
            val multipart = getMultipartBodyPart(uri)
            if (multipart == null) {
                _uiState.value = TareaAlumnoDetalleUiState.Error("No se pudo leer el archivo seleccionado")
                return@launch
            }
            studentTaskRepository.uploadFile(multipart).onSuccess { res ->
                val request = SubmitTaskRequestRemote(
                    attachmentUrl = res.data.attachmentUrl
                )
                studentTaskRepository.submitTask(previa.id, request).onSuccess { entregada ->
                    _uiState.value = TareaAlumnoDetalleUiState.Success(tarea, entregada)
                }.onFailure { e ->
                    _uiState.value = TareaAlumnoDetalleUiState.Error(e.message ?: "Error al registrar la entrega")
                }
            }.onFailure { e ->
                _uiState.value = TareaAlumnoDetalleUiState.Error(e.message ?: "Error al subir el archivo")
            }
        }
    }

    fun anularEntrega(taskId: Int, enrollmentId: Int) {
        val estado = _uiState.value
        if (estado !is TareaAlumnoDetalleUiState.Success) return
        val tarea = estado.tarea
        val previa = estado.entregaPrevia ?: return
        viewModelScope.launch {
            _uiState.value = TareaAlumnoDetalleUiState.Enviando(tarea)
            studentTaskRepository.unsubmitTask(previa.id).onSuccess { limpia ->
                _uiState.value = TareaAlumnoDetalleUiState.Success(tarea, limpia)
            }.onFailure { e ->
                _uiState.value = TareaAlumnoDetalleUiState.Error(e.message ?: "Error al anular entrega")
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
