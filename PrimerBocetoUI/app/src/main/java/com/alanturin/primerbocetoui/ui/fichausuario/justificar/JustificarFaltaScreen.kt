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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.ui.camera.CameraScreen
import com.alanturin.primerbocetoui.ui.camera.CameraViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun JustificarFaltaScreen(
    id: Int,
    subjectName: String,
    date: String,
    startTime: String,
    status: String,
    modifier: Modifier = Modifier,
    viewModel: JustificarFaltaViewModel = hiltViewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lastPhotoUri by cameraViewModel.lastPhotoUri.collectAsState()
    var showCamera by remember { mutableStateOf(false) }
    var hasRequestedPermission by remember { mutableStateOf(false) }
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
    LaunchedEffect(cameraPermissionState.status.isGranted) {
        if (cameraPermissionState.status.isGranted && hasRequestedPermission) {
            showCamera = true
            hasRequestedPermission = false
        }
    }
    // se guarda uri
    LaunchedEffect(lastPhotoUri) {
        lastPhotoUri?.let { uri ->
            viewModel.onFileSelected(uri)
            cameraViewModel.resetLastPhotoUri()
            showCamera = false
        }
    }
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.onFileSelected(it) }
    }
    if (showCamera) {
        CameraScreen(
            viewModel = cameraViewModel,
            onPhotoTaken = { },
            onBack = { showCamera = false }
        )
    } else {
        Column(
            modifier = modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(stringResource(R.string.justificar_falta_title), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(stringResource(R.string.justificar_falta_subject, subjectName), fontWeight = FontWeight.Bold)
                    Text(stringResource(R.string.justificar_falta_date, date))
                    Text(stringResource(R.string.justificar_falta_time, startTime))
                }
            }
            Button(
                onClick = {
                    if (cameraPermissionState.status.isGranted) {
                        showCamera = true
                    } else {
                        hasRequestedPermission = true
                        cameraPermissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(Icons.Default.CameraAlt, null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.justificar_falta_photo))
            }
            OutlinedButton(
                onClick = { filePickerLauncher.launch("*/*") },
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Icon(Icons.Default.UploadFile, null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.justificar_falta_upload))
            }
            if (uiState is JustificarFaltaViewModel.UiState.FileSelected) {
                val uri = (uiState as JustificarFaltaViewModel.UiState.FileSelected).uri
                AsyncImage(
                    model = uri,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Button(
                    onClick = { viewModel.enviarJustificacion(id, subjectName, date) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
                ) {
                    Text(stringResource(R.string.justificar_falta_send))
                }
            }

            if (uiState is JustificarFaltaViewModel.UiState.Success) {
                Text(stringResource(R.string.justificar_falta_success), color = Color(0xFF10B981), modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}