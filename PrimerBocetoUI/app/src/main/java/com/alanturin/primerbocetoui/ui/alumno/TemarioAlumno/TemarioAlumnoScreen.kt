package com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TemarioAlumnoScreen(
    enrollmentId: Int,
    asignaturaNombre: String,
    onBack: () -> Unit,
    onTareaClick: (taskId: Int, enrollmentId: Int) -> Unit,
    viewModel: TemarioAlumnoViewModel = hiltViewModel()
) {
    LaunchedEffect(enrollmentId) {
        viewModel.loadTasks(enrollmentId)
    }
    val tareasReales by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(asignaturaNombre, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        if (tareasReales.isEmpty()) {
            // por termianr --> no salen las tareas pero si estan creadas en la bd
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No tienes tareas asignadas",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(tareasReales) { index, studentTask ->
                    val task = studentTask.task
                    TaskCard(
                        index = index + 1,
                        titulo = task?.title,
                        descripcion = task?.description,
                        tipo = task?.type,
                        estado = studentTask.status,
                        onClick = { onTareaClick(studentTask.idTask, enrollmentId) }
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(index: Int, titulo: String?, descripcion: String?, tipo: String?, estado: String?, onClick: () -> Unit) {
    val containerColor = when (tipo) {
        "THEORY" -> Color(0xFFE3F2FD)
        "HOMEWORK" -> Color(0xFFF3E5F5)
        else -> Color(0xFFF5F5F5)
    }
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "$index. ${titulo ?: "Tarea sin título"}", fontWeight = FontWeight.Bold)
            Text(text = descripcion ?: "Sin descripción adicional.", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = if (estado == "PENDING") Color(0xFFFFEBEE) else Color(0xFFE8F5E9),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = estado ?: "PENDIENTE",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = if (estado == "PENDING") Color.Red else Color(0xFF2E7D32),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}