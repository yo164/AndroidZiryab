package com.alanturin.primerbocetoui.ui.profesor.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.repository.task.TaskRepository
import com.alanturin.primerbocetoui.ui.session.AssignmentSessionService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val assignmentSessionService: AssignmentSessionService
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _taskCreated = MutableStateFlow(false)
    val taskCreated: StateFlow<Boolean> = _taskCreated.asStateFlow()

    fun createTask(
        title: String,
        description: String,
        type: String,
        startDate: String,
        dueDate: String,
        schoolYear: String
    ) {
        val idAssignment = assignmentSessionService.currentAssignmentId.value
        if (idAssignment == null) {
            _error.value = "No hay asignatura seleccionada"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val request = CreateTaskRequestRemote(
                idTeacherAssignment = idAssignment.toInt(),
                title = title,
                description = description.ifBlank { null },
                type = type,
                startDate = startDate,
                dueDate = dueDate,
                schoolYear = schoolYear
            )

            val result = repository.createTask(request)
            if (result.isSuccess) {
                _taskCreated.value = true
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Error al crear la tarea"
            }

            _isLoading.value = false
        }
    }

    fun resetTaskCreated() {
        _taskCreated.value = false
    }
}
