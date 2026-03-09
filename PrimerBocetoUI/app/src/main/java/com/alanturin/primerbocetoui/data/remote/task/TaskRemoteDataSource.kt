package com.alanturin.primerbocetoui.data.remote.task

import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote

interface TaskRemoteDataSource {

    suspend fun createTask(request: com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote): Result<TaskItemRemote>

}