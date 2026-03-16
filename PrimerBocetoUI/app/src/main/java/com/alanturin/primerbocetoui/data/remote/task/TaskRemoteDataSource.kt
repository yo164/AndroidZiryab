package com.alanturin.primerbocetoui.data.remote.task

import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote

interface TaskRemoteDataSource {

    suspend fun createTask(request: CreateTaskRequestRemote): Result<TaskItemRemote>

}