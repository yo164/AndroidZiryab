package com.alanturin.primerbocetoui.data.remote.task

import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import javax.inject.Inject

class TaskRemoteDataSourceImpl  @Inject constructor(
    private val api: TaskApi
) : TaskRemoteDataSource {

    override suspend fun createTask(request: CreateTaskRequestRemote): Result<TaskItemRemote> {
        return try {
            val response = api.createTask(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    Result.failure(RuntimeException("Body vacío"))
                } else {
                    Result.success(body.data)
                }
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}