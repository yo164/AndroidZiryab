package com.alanturin.primerbocetoui.ui.profesor.tasks

import com.alanturin.primerbocetoui.domain.model.TeacherTask

sealed interface TaskListUiState {
    data object Initial : TaskListUiState
    data object Loading : TaskListUiState
    data object Empty : TaskListUiState
    data object NoAssignment : TaskListUiState
    data class Error(val message: String) : TaskListUiState
    data class Content(val sections: List<TaskSectionUi>) : TaskListUiState
}

data class TaskSectionUi(
    val dueDateIso: String,
    val tasks: List<TeacherTask>
)
