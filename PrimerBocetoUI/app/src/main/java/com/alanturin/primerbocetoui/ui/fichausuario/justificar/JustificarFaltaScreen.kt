package com.alanturin.primerbocetoui.ui.fichausuario.justificar

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.ui.camera.CameraScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun JustificarFaltaScreen(
    subjectName: String,
    date: String,
    startTime: String,
    status: String,
    modifier: Modifier = Modifier,
    viewModel: JustificarFaltaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showCamera by remember { mutableStateOf(false) }

    // Estado para gestionar los permisos de la cámara
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    // Lanzador para seleccionar archivos del dispositivo
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onFileSelected(it) }
    }

    if (showCamera) {
        CameraScreen(
            onPhotoTaken = { uri ->
                viewModel.onPhotoTaken(uri)
                showCamera = false
            },
            onBack = { showCamera = false }
        )
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Justificar Falta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Asignatura: $subjectName", fontWeight = FontWeight.Bold)
                    Text("Fecha: $date")
                    Text("Hora: $startTime")
                    Text("Estado: $status", color = if (status == "MISSING") Color.Red else Color.DarkGray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para usar la Cámara
            Button(
                onClick = {
                    if (cameraPermissionState.status.isGranted) {
                        showCamera = true
                    } else {
                        cameraPermissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Hacer foto del justificante")
            }

            // Botón para elegir archivo del dispositivo
            OutlinedButton(
                onClick = { filePickerLauncher.launch("*/*") },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(Icons.Default.UploadFile, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Subir desde el dispositivo")
            }

            // Feedback y botón final de envío
            when (uiState) {
                is JustificarFaltaViewModel.UiState.FileSelected -> {
                    Text(
                        text = "✓ Archivo adjuntado correctamente",
                        color = Color(0xFF10B981),
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Button(
                        onClick = { viewModel.enviarJustificacion(subjectName, date) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                    ) {
                        Text("Confirmar y Enviar")
                    }
                }
                is JustificarFaltaViewModel.UiState.Sending -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
                is JustificarFaltaViewModel.UiState.Success -> {
                    Text(
                        text = "¡Enviado con éxito!",
                        color = Color(0xFF10B981),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {}
            }
        }
    }
}