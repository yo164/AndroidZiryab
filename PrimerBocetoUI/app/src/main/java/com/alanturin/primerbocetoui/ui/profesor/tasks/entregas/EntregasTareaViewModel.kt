package com.alanturin.primerbocetoui.ui.profesor.tasks.entregas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.repository.studenttask.StudentTaskRepository
import com.alanturin.primerbocetoui.data.repository.task.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntregasTareaViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val studentTaskRepository: StudentTaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EntregasTareaUiState>(EntregasTareaUiState.Loading)
    val uiState: StateFlow<EntregasTareaUiState> = _uiState.asStateFlow()

    fun loadData(taskId: Int) {
        viewModelScope.launch {
            _uiState.value = EntregasTareaUiState.Loading
            
            val taskResult = taskRepository.getTaskById(taskId)
            val submissionsResult = studentTaskRepository.getSubmissionsByTask(taskId)

            if (taskResult.isSuccess && submissionsResult.isSuccess) {
                _uiState.value = EntregasTareaUiState.Success(
                    task = taskResult.getOrThrow(),
                    submissions = submissionsResult.getOrThrow()
                )
            } else {
                val errorMessage = when {
                    taskResult.isFailure -> taskResult.exceptionOrNull()?.message ?: "Error al cargar la tarea"
                    submissionsResult.isFailure -> submissionsResult.exceptionOrNull()?.message ?: "Error al cargar las entregas"
                    else -> "Error desconocido"
                }
                _uiState.value = EntregasTareaUiState.Error(errorMessage)
            }
        }
    }
}
