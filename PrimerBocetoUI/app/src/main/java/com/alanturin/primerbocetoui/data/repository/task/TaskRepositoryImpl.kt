package com.alanturin.primerbocetoui.data.repository.task

import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.task.TaskRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: TaskRemoteDataSource
) : TaskRepository {

    override suspend fun createTask(request: CreateTaskRequestRemote): Result<TaskItemRemote> {
        return remoteDataSource.createTask(request)
    }
}