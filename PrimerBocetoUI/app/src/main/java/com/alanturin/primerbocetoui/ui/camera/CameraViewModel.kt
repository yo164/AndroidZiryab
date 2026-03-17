package com.alanturin.primerbocetoui.ui.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.*
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.guava.await
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    private val _cameraSelector = MutableStateFlow(DEFAULT_BACK_CAMERA)
    val cameraSelector: StateFlow<CameraSelector> = _cameraSelector.asStateFlow()
    private val _surfaceRequest = MutableStateFlow<SurfaceRequest?>(null)
    val surfaceRequest: StateFlow<SurfaceRequest?> = _surfaceRequest.asStateFlow()
    private val _captureMessage = MutableStateFlow<String?>(null)
    val captureMessage: StateFlow<String?> = _captureMessage.asStateFlow()
    private val _lastPhotoUri = MutableStateFlow<Uri?>(null)
    val lastPhotoUri: StateFlow<Uri?> = _lastPhotoUri.asStateFlow()
    private val cameraPreviewUseCase = Preview.Builder().build().apply {
        setSurfaceProvider { newSurfaceRequest -> _surfaceRequest.value = newSurfaceRequest }
    }
    private val imageCaptureUseCase = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
        .build()
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    fun bindToCamera(context: Context, lifecycleOwner: LifecycleOwner) {
        viewModelScope.launch {
            val processCameraProvider = ProcessCameraProvider.getInstance(context).await()
            cameraSelector.collect { selector ->
                try {
                    processCameraProvider.unbindAll()
                    processCameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        cameraPreviewUseCase,
                        imageCaptureUseCase
                    )
                } catch (e: Exception) {
                    Log.e("CameraVM", "Error binding camera", e)
                }
            }
        }
    }
    fun cambiarCamara() {
        _cameraSelector.value = if (_cameraSelector.value == DEFAULT_BACK_CAMERA)
            DEFAULT_FRONT_CAMERA else DEFAULT_BACK_CAMERA
    }
    fun tomarFoto(context: Context) {
        val photoFile = File(
            context.getExternalFilesDir(null),
            SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(Date()) + ".jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCaptureUseCase.takePicture(
            outputOptions,
            cameraExecutor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    copiarAGaleria(context, photoFile)
                }
                override fun onError(error: ImageCaptureException) {
                    _captureMessage.value = "Error al capturar"
                }
            }
        )
    }
    private fun copiarAGaleria(context: Context, photoFile: File) {
        try {
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, photoFile.name)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/ZiryabApp")
                }
            }
            val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            uri?.let {
                context.contentResolver.openOutputStream(it)?.use { out ->
                    FileInputStream(photoFile).use { ins -> ins.copyTo(out) }
                }
                _lastPhotoUri.value = it
                _captureMessage.value = "Foto guardada en galería"
                photoFile.delete()
            }
        } catch (e: Exception) {
            _captureMessage.value = "Error al guardar"
        }
    }
    fun limpiarMensaje() {
        _captureMessage.value = null
    }
    fun resetLastPhotoUri() {
        _lastPhotoUri.value = null
    }
    override fun onCleared() {
        super.onCleared()
        cameraExecutor.shutdown()
    }
}