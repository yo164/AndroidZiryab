package com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                        enviada = false,
                        onEntregar = { viewModel.entregar(taskId, enrollmentId) }
                    )
                }
                is TareaAlumnoDetalleUiState.Enviando -> {
                    DetalleContent(
                        tarea = state.tarea,
                        entregaPrevia = null,
                        enviando = true,
                        enviada = false,
                        onEntregar = {}
                    )
                }
                is TareaAlumnoDetalleUiState.Enviada -> {
                    DetalleContent(
                        tarea = state.tarea,
                        entregaPrevia = null,
                        enviando = false,
                        enviada = true,
                        onEntregar = {}
                    )
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
    enviada: Boolean,
    onEntregar: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = when (tarea.type) {
        "THEORY" -> Color(0xFFE3F2FD)
        "HOMEWORK" -> Color(0xFFF3E5F5)
        else -> Color(0xFFF5F5F5)
    }
    val estadoTexto = entregaPrevia?.status ?: "PENDING"
    val statusYaEntregada = setOf("SUBMITTED", "LATE", "GRADED")
    val yaEntregada = (entregaPrevia?.status in statusYaEntregada) || enviada

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
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
                Spacer(modifier = Modifier.height(8.dp))
                Surface(
                    color = if (estadoTexto == "PENDING") Color(0xFFFFEBEE) else Color(0xFFE8F5E9),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.task_detail_status_label, estadoTexto),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = if (estadoTexto == "PENDING") Color.Red else Color(0xFF2E7D32),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (enviada) {
            Text(
                text = stringResource(R.string.task_detail_submitted_success),
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.SemiBold
            )
        }

        Button(
            onClick = onEntregar,
            enabled = !yaEntregada && !enviando,
            modifier = Modifier.fillMaxWidth()
        ) {
            val textoBoton = when {
                enviando -> stringResource(R.string.task_detail_submitting)
                yaEntregada -> stringResource(R.string.task_detail_submitted_label)
                else -> stringResource(R.string.task_detail_submit_button)
            }
            Text(textoBoton)
        }
    }
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
