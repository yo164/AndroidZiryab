package com.alanturin.primerbocetoui.ui.camera

import android.Manifest
import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    viewModel: CameraViewModel,
    onPhotoTaken: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val surfaceRequest by viewModel.surfaceRequest.collectAsStateWithLifecycle()
    val cameraSelector by viewModel.cameraSelector.collectAsStateWithLifecycle()
    val captureMessage by viewModel.captureMessage.collectAsStateWithLifecycle()

    LaunchedEffect(captureMessage) {
        if (captureMessage == "Foto guardada en galería") {
            onPhotoTaken()
        }
    }
    if (permissionState.status.isGranted) {
        LaunchedEffect(cameraSelector) {
            viewModel.bindToCamera(context, lifecycleOwner)
        }
        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            surfaceRequest?.let {
                CameraXViewfinder(
                    surfaceRequest = it,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 48.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { viewModel.cambiarCamara() },
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
                    ) {
                        Icon(Icons.Default.FlipCameraAndroid, contentDescription = "Girar", tint = Color.White)
                    }
                    Button(
                        onClick = { viewModel.tomarFoto(context) },
                        shape = CircleShape,
                        modifier = Modifier
                            .size(80.dp)
                            .border(4.dp, Color.White, CircleShape),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                    Spacer(modifier = Modifier.size(48.dp))
                }
            }
        }
    } else {
        LaunchedEffect(Unit) { permissionState.launchPermissionRequest() }
    }
}