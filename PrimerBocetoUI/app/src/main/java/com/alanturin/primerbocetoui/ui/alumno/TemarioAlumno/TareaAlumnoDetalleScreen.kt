package com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno

import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TareaAlumnoDetalleScreen(
    taskId: Int,
    enrollmentId: Int,
    onBack: () -> Unit,
    viewModel: TareaAlumnoDetalleViewModel = hiltViewModel()
) {
    LaunchedEffect(taskId) { viewModel.cargar(taskId, enrollmentId) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.task_detail_title),
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.task_detail_cd_back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = uiState) {
                is TareaAlumnoDetalleUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is TareaAlumnoDetalleUiState.Error -> {
                    ErrorBlock(
                        mensaje = state.mensaje,
                        onReintentar = { viewModel.cargar(taskId, enrollmentId) },
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is TareaAlumnoDetalleUiState.Success -> {
                    DetalleContent(
                        tarea = state.tarea,
                        entregaPrevia = state.entregaPrevia,
                        enviando = false,
                        onEntregarUrl = { url -> viewModel.entregarConUrl(taskId, enrollmentId, url) },
                        onEntregarArchivo = { uri -> viewModel.entregarConArchivo(taskId, enrollmentId, uri) },
                        onAnular = { viewModel.anularEntrega(taskId, enrollmentId) }
                    )
                }
                is TareaAlumnoDetalleUiState.Enviando -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator()
                            Spacer(Modifier.height(8.dp))
                            Text("Enviando entrega...")
                        }
                    }
                }
                is TareaAlumnoDetalleUiState.Enviada -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun DetalleContent(
    tarea: TaskItemRemote,
    entregaPrevia: StudentTaskItemRemote?,
    enviando: Boolean,
    onEntregarUrl: (String) -> Unit,
    onEntregarArchivo: (Uri) -> Unit,
    onAnular: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when (tarea.type) {
        "THEORY" -> Color(0xFFE3F2FD)
        "HOMEWORK" -> Color(0xFFF3E5F5)
        else -> Color(0xFFF5F5F5)
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = containerColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = tarea.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tarea.description ?: stringResource(R.string.task_detail_no_description),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.task_detail_due_date, tarea.dueDate),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        val estadoTexto = entregaPrevia?.status ?: "PENDING"
        val yaEntregada = estadoTexto in setOf("SUBMITTED", "LATE", "GRADED")

        if (yaEntregada) {
            val badgeColor = when (estadoTexto) {
                "SUBMITTED" -> Color(0xFFE8F5E9)
                "LATE" -> Color(0xFFFFF3E0)
                "GRADED" -> Color(0xFFF3E5F5)
                else -> Color(0xFFFFEBEE)
            }
            val badgeTextColor = when (estadoTexto) {
                "SUBMITTED" -> Color(0xFF2E7D32)
                "LATE" -> Color(0xFFE65100)
                "GRADED" -> Color(0xFF7B1FA2)
                else -> Color.Red
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tu entrega",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Surface(
                            color = badgeColor,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = estadoTexto,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                color = badgeTextColor,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (entregaPrevia?.attachmentUrl != null) {
                        val isUrl = entregaPrevia.attachmentUrl.startsWith("http")
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isUrl) Icons.Default.Link else Icons.Default.AttachFile,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = entregaPrevia.attachmentUrl,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF1976D2),
                                maxLines = 2
                            )
                        }
                    } else {
                        Text(
                            text = "Entregada sin archivo adjunto",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }

                    if (entregaPrevia?.submissionDate != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Fecha de entrega: ${entregaPrevia.submissionDate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }

            if (estadoTexto == "GRADED") {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD1C4E9))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFF7B1FA2))
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = "Calificación",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7B1FA2)
                            )
                        }
                        Text(
                            text = "Nota: ${entregaPrevia?.score ?: 0.0} / 10.0",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7B1FA2)
                        )
                        if (entregaPrevia?.feedback != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Comentarios del profesor:",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF7B1FA2)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "\"${entregaPrevia?.feedback}\"",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF4A148C),
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            }

            if (estadoTexto != "GRADED" && !enviando) {
                Button(
                    onClick = onAnular,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color.Red),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Anular entrega")
                }
            }
        } else {
            var selectedTab by remember { mutableStateOf(0) }
            TabRow(selectedTabIndex = selectedTab, modifier = Modifier.fillMaxWidth()) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Link, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Pegar Enlace")
                    }
                }
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.AttachFile, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Subir Archivo")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (selectedTab == 0) {
                var urlText by remember { mutableStateOf("") }
                OutlinedTextField(
                    value = urlText,
                    onValueChange = { urlText = it },
                    label = { Text("URL de la entrega") },
                    placeholder = { Text("https://example.com/mi-trabajo") },
                    leadingIcon = { Icon(Icons.Default.Link, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onEntregarUrl(urlText) },
                    enabled = urlText.isNotBlank() && !enviando,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Entregar Enlace")
                }
            } else {
                val context = LocalContext.current
                var fileUri by remember { mutableStateOf<Uri?>(null) }
                val filePickerLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent()
                ) { uri: Uri? ->
                    fileUri = uri
                }

                if (fileUri != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF81C784))
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                Icon(Icons.Default.AttachFile, contentDescription = null, tint = Color(0xFF2E7D32))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    text = getFileName(context, fileUri!!),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E7D32),
                                    maxLines = 1
                                )
                            }
                            IconButton(onClick = { fileUri = null }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { onEntregarArchivo(fileUri!!) },
                        enabled = !enviando,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Subir y Entregar")
                    }
                } else {
                    OutlinedButton(
                        onClick = { filePickerLauncher.launch("*/*") },
                        modifier = Modifier.fillMaxWidth().height(100.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.AttachFile, contentDescription = null)
                            Spacer(Modifier.height(8.dp))
                            Text("Seleccionar archivo del dispositivo")
                        }
                    }
                }
            }
        }
    }
}

private fun getFileName(context: android.content.Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        try {
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index != -1) {
                    result = cursor.getString(index)
                }
            }
        } finally {
            cursor?.close()
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "Archivo seleccionado"
}

@Composable
private fun ErrorBlock(
    mensaje: String,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.task_detail_error_loading),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = mensaje,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Button(onClick = onReintentar) {
            Text(stringResource(R.string.task_detail_retry_button))
        }
    }
}
