package com.alanturin.primerbocetoui.data.remote.task

import com.alanturin.primerbocetoui.data.remote.model.*
import org.json.JSONObject
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
                val errorBody = response.errorBody()?.string()
                val message = mensajeErrorApi(response.code(), errorBody)
                Result.failure(RuntimeException(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTaskById(id: Int): Result<TaskItemRemote> {
        return try {
            val response = api.getTaskById(id)
            if (response.isSuccessful) {
                response.body()?.data?.let { Result.success(it) }
                    ?: Result.failure(RuntimeException("Body vacío"))
            } else {
                val errorBody = response.errorBody()?.string()
                val message = mensajeErrorApi(response.code(), errorBody)
                Result.failure(RuntimeException(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTasksByTeacherAssignment(idTeacherAssignment: Long): Result<List<TaskItemRemote>> {
        return try {
            val response = api.getTasksByTeacherAssignment(idTeacherAssignment)
            if (response.isSuccessful) {
                Result.success(response.body()?.data.orEmpty())
            } else {
                val errorBody = response.errorBody()?.string()
                val message = mensajeErrorApi(response.code(), errorBody)
                Result.failure(RuntimeException(message))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun mensajeErrorApi(codigo: Int, cuerpo: String?): String {
        val desdeJson = runCatching {
            cuerpo?.let { JSONObject(it).optString("message") }
        }.getOrNull().orEmpty()
        return desdeJson.ifBlank { "Error: $codigo" }
    }
}