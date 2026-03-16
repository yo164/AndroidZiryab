package com.alanturin.primerbocetoui.ui.profesor.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel = hiltViewModel()) {

    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val taskCreated by viewModel.taskCreated.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    // Cuando se crea la tarea, cerrar el diálogo
    LaunchedEffect(taskCreated) {
        if (taskCreated) {
            showDialog = false
            viewModel.resetTaskCreated()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Crear tarea")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Tareas",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }

    if (showDialog) {
        CreateTaskDialog(
            isLoading = isLoading,
            error = error,
            onDismiss = { showDialog = false },
            onConfirm = { title, description, type, startDate, dueDate, schoolYear ->
                viewModel.createTask(title, description, type, startDate, dueDate, schoolYear)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateTaskDialog(
    isLoading: Boolean,
    error: String?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("HOMEWORK") }
    var startDate by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var schoolYear by remember { mutableStateOf("2025-2026") }
    var expanded by remember { mutableStateOf(false) }

    val taskTypes = listOf("EXAM", "HOMEWORK", "PROJECT")

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = { Text("Nueva tarea") },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descripción (opcional)") },
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                // Tipo de tarea
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedType,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Tipo") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        taskTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Fecha inicio (yyyy-MM-dd)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = dueDate,
                    onValueChange = { dueDate = it },
                    label = { Text("Fecha entrega (yyyy-MM-dd)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = schoolYear,
                    onValueChange = { schoolYear = it },
                    label = { Text("Año escolar") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                if (isLoading) {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }

                if (error != null) {
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(title, description, selectedType, startDate, dueDate, schoolYear)
                },
                enabled = title.isNotBlank() && startDate.isNotBlank() && dueDate.isNotBlank() && !isLoading
            ) {
                Text("Crear")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isLoading) {
                Text("Cancelar")
            }
        }
    )
}