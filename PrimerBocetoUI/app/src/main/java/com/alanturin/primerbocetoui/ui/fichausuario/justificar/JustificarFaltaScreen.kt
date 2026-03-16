package com.alanturin.primerbocetoui.ui.fichausuario.justificar

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.UploadFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
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

    // permisos camara
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    // selector de archivos del sistema
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
            val isTest = status == "TEST"
            Text(
                text = if (isTest) "Prueba de Cámara" else "Justificar Falta",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = if (isTest) Color.Gray else MaterialTheme.colorScheme.onSurface
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = if (isTest) Color(0xFFE5E7EB) else Color(0xFFF3F4F6)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Asignatura: $subjectName", fontWeight = FontWeight.Bold)
                    Text("Fecha: $date")
                    Text("Hora: $startTime")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // camara
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

            // abrir archivos
            OutlinedButton(
                onClick = { filePickerLauncher.launch("*/*") },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(Icons.Default.UploadFile, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Subir desde el dispositivo")
            }

            if (uiState is JustificarFaltaViewModel.UiState.FileSelected) {
                val uri = (uiState as JustificarFaltaViewModel.UiState.FileSelected).uri

                Text(
                    text = "Vista previa del archivo:",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                AsyncImage(
                    model = uri,
                    contentDescription = "Justificante",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CheckCircle, "OK", tint = Color(0xFF10B981))
                    Spacer(Modifier.width(8.dp))
                    Text("Captura lista para enviar", color = Color(0xFF10B981))
                }

                Button(
                    onClick = { viewModel.enviarJustificacion(subjectName, date) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                ) {
                    Text(if (isTest) "Finalizar prueba justificante de Mockeo" else "Enviar Justificante")
                }
            }

            if (uiState is JustificarFaltaViewModel.UiState.Sending) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            if (uiState is JustificarFaltaViewModel.UiState.Success) {
                Text(
                    text = if (isTest) "¡Prueba del justificante completada!" else "¡Enviado con éxito!",
                    color = Color(0xFF10B981),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}