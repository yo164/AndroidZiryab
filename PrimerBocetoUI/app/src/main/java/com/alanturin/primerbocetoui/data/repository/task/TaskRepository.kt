package com.alanturin.primerbocetoui.data.repository.task

import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote

interface TaskRepository {
    suspend fun createTask(request: CreateTaskRequestRemote): Result<TaskItemRemote>

    suspend fun getTasksForSubject(subjectId: Long): Result<List<TaskItemRemote>>
}