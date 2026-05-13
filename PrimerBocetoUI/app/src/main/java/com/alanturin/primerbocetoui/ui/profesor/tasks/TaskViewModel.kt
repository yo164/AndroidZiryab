package com.alanturin.primerbocetoui.ui.profesor.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.repository.task.TaskRepository
import com.alanturin.primerbocetoui.domain.model.TeacherTask
import com.alanturin.primerbocetoui.ui.session.AssignmentSessionService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    private val assignmentSessionService: AssignmentSessionService
) : ViewModel() {

    private val _listUiState = MutableStateFlow<TaskListUiState>(TaskListUiState.Initial)
    val listUiState: StateFlow<TaskListUiState> = _listUiState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _taskCreated = MutableStateFlow(false)
    val taskCreated: StateFlow<Boolean> = _taskCreated.asStateFlow()

    init {
        viewModelScope.launch {
            assignmentSessionService.currentAssignmentId.collect { id ->
                if (id != null) {
                    loadTasksInternal(id)
                } else {
                    _listUiState.value = TaskListUiState.NoAssignment
                }
            }
        }
    }

    fun refreshTasks() {
        val id = assignmentSessionService.currentAssignmentId.value ?: return
        viewModelScope.launch { loadTasksInternal(id) }
    }

    private suspend fun loadTasksInternal(idTeacherAssignment: Long) {
        _listUiState.value = TaskListUiState.Loading
        repository.getTasksByTeacherAssignment(idTeacherAssignment).fold(
            onSuccess = { tasks ->
                _listUiState.value = when {
                    tasks.isEmpty() -> TaskListUiState.Empty
                    else -> TaskListUiState.Content(sectionsGroupedByDueDate(tasks))
                }
            },
            onFailure = { e ->
                _listUiState.value = TaskListUiState.Error(
                    e.message ?: "Error"
                )
            }
        )
    }

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
                loadTasksInternal(idAssignment)
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Error al crear la tarea"
            }

            _isLoading.value = false
        }
    }

    fun resetTaskCreated() {
        _taskCreated.value = false
    }

    private fun sectionsGroupedByDueDate(tasks: List<TeacherTask>): List<TaskSectionUi> {
        return tasks
            .groupBy { it.dueDate }
            .map { (due, list) ->
                TaskSectionUi(
                    dueDateIso = due,
                    tasks = list.sortedByDescending { it.createdAt }
                )
            }
            .sortedWith(compareBy { section -> parseDueDateOrMax(section.dueDateIso) })
    }

    private fun parseDueDateOrMax(iso: String): LocalDate {
        return try {
            LocalDate.parse(iso)
        } catch (_: DateTimeParseException) {
            LocalDate.MAX
        }
    }
}
