package com.alanturin.primerbocetoui.ui.profesor.tasks.entregas

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote

sealed interface EntregasTareaUiState {
    data object Loading : EntregasTareaUiState
    data class Success(
        val task: TaskItemRemote,
        val submissions: List<StudentTaskItemRemote>
    ) : EntregasTareaUiState
    data class Error(val message: String) : EntregasTareaUiState
}
