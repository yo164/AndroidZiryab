package com.alanturin.primerbocetoui.data.repository.task

import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import com.alanturin.primerbocetoui.domain.model.TeacherTask

interface TaskRepository {
    suspend fun createTask(request: CreateTaskRequestRemote): Result<TaskItemRemote>

    suspend fun getTaskById(id: Int): Result<TaskItemRemote>

    suspend fun getTasksByTeacherAssignment(idTeacherAssignment: Long): Result<List<TeacherTask>>
}