package com.alanturin.primerbocetoui.ui.profesor.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Switch
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.domain.model.TeacherTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(viewModel: TaskViewModel = hiltViewModel(), onTaskClick: (Int) -> Unit) {
    val listUiState by viewModel.listUiState.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()
    val taskCreated by viewModel.taskCreated.collectAsStateWithLifecycle()

    var showDialog by remember { mutableStateOf(false) }

    // Cuando se crea la tarea, cerrar el diálogo
    LaunchedEffect(taskCreated) {
        if (taskCreated) {
            showDialog = false
            viewModel.resetTaskCreated()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = stringResource(R.string.task_title)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.task_cd_create))
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (val state = listUiState) {
                TaskListUiState.Initial,
                TaskListUiState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                TaskListUiState.NoAssignment -> {
                    EmptyStateMessage(text = stringResource(R.string.task_no_assignment))
                }
                TaskListUiState.Empty -> {
                    EmptyStateMessage(text = stringResource(R.string.task_empty_list))
                }
                is TaskListUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.message,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        TextButton(
                            onClick = { viewModel.refreshTasks() },
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(stringResource(R.string.task_retry))
                        }
                    }
                }
                is TaskListUiState.Content -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        state.sections.forEach { section ->
                            item(key = "header_${section.dueDateIso}") {
                                TaskSectionHeader(dueDateIso = section.dueDateIso)
                            }
                            items(
                                items = section.tasks,
                                key = { it.id }
                            ) { task ->
                                TeacherTaskCard(task = task, onClick = { onTaskClick(task.id) })
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog) {
        CreateTaskDialog(
            isLoading = isLoading,
            error = error,
            onDismiss = { showDialog = false },
            onConfirm = { title, description, type, startDate, dueDate, schoolYear, isPublished, allowLateSubmission ->
                viewModel.createTask(title, description, type, startDate, dueDate, schoolYear, isPublished, allowLateSubmission)
            }
        )
    }
}

@Composable
private fun EmptyStateMessage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun TaskSectionHeader(dueDateIso: String) {
    Text(
        text = stringResource(R.string.task_section_due, dueDateIso),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Composable
private fun TeacherTaskCard(task: TeacherTask, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = when (task.type) {
                    "EXAM" -> stringResource(R.string.task_type_exam)
                    "PROJECT" -> stringResource(R.string.task_type_project)
                    else -> stringResource(R.string.task_type_homework)
                },
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = stringResource(R.string.task_item_dates, task.startDate, task.dueDate),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            task.description?.takeIf { it.isNotBlank() }?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateTaskDialog(
    isLoading: Boolean,
    error: String?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String, String, String, Boolean, Boolean) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("HOMEWORK") }
    var startDate by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var schoolYear by remember { mutableStateOf("2025-2026") }
    var isPublished by remember { mutableStateOf(true) }
    var allowLateSubmission by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    var pickingForStart by remember { mutableStateOf(true) }

    val taskTypes = listOf("EXAM", "HOMEWORK", "PROJECT")

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = { Text(stringResource(R.string.task_dialog_title)) },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text(stringResource(R.string.task_label_title)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.task_label_description)) },
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
                        label = { Text(stringResource(R.string.task_label_type)) },
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

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text(stringResource(R.string.task_label_start_date)) },
                        supportingText = { Text("Click para seleccionar fecha") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable(enabled = !isLoading) {
                                pickingForStart = true
                                showDatePicker = true
                            }
                    )
                }

                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { },
                        readOnly = true,
                        label = { Text(stringResource(R.string.task_label_due_date)) },
                        supportingText = { Text("Click para seleccionar fecha") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = true
                    )
                    // Capa invisible con el clickable encima
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable(enabled = !isLoading) {
                                pickingForStart = false
                                showDatePicker = true
                            }
                    )
                }

                OutlinedTextField(
                    value = schoolYear,
                    onValueChange = { schoolYear = it },
                    label = { Text(stringResource(R.string.task_label_school_year)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.task_label_publish))
                    Switch(checked = isPublished, onCheckedChange = { isPublished = it })
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.task_label_allow_late))
                    Switch(checked = allowLateSubmission, onCheckedChange = { allowLateSubmission = it })
                }

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
                    onConfirm(title, description, selectedType, startDate, dueDate, schoolYear, isPublished, allowLateSubmission)
                },
                enabled = title.isNotBlank() && startDate.isNotBlank() && dueDate.isNotBlank() && !isLoading
            ) {
                Text(stringResource(R.string.task_button_create))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isLoading) {
                Text(stringResource(R.string.task_button_cancel))
            }
        }
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val instant = java.time.Instant.ofEpochMilli(millis)
                        val date = java.time.LocalDateTime.ofInstant(instant, java.time.ZoneId.of("UTC"))
                        val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                        val formatted = date.format(formatter)
                        
                        if (pickingForStart) {
                            startDate = formatted
                        } else {
                            dueDate = formatted
                        }
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
