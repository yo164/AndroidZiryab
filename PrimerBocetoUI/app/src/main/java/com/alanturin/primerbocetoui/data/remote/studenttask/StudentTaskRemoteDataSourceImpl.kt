package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.GradeSubmissionRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.UploadFileResponseRemote
import javax.inject.Inject

class StudentTaskRemoteDataSourceImpl @Inject constructor(
    private val api: StudentTaskApi
) : StudentTaskRemoteDataSource {
    override suspend fun getByStudentEnrollment(enrollmentId: Int): Result<List<StudentTaskItemRemote>> {
        return try {
            val response = api.getByStudentEnrollment(enrollmentId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(RuntimeException("Cuerpo de respuesta nulo"))
                }
            } else {
                Result.failure(RuntimeException("Error en la API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun submitTask(id: Int, request: SubmitTaskRequestRemote): Result<StudentTaskItemRemote> {
        return try {
            val response = api.submitTask(id, request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(RuntimeException("Cuerpo de respuesta nulo"))
                }
            } else {
                Result.failure(RuntimeException("Error en la API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getSubmissionsByTask(taskId: Int): Result<List<StudentTaskItemRemote>> {
        return try {
            val response = api.getSubmissionsByTask(taskId)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(RuntimeException("Cuerpo de respuesta nulo"))
                }
            } else {
                Result.failure(RuntimeException("Error en la API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun gradeSubmission(
        id: Int,
        request: GradeSubmissionRequestRemote
    ): Result<StudentTaskItemRemote> {
        return try {
            val response = api.gradeSubmission(id, request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(RuntimeException("Cuerpo de respuesta nulo"))
                }
            } else {
                Result.failure(RuntimeException("Error en la API: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadFile(file: okhttp3.MultipartBody.Part): Result<UploadFileResponseRemote> {
        return try {
            val response = api.uploadFile(file)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(RuntimeException("Cuerpo de respuesta nulo"))
                }
            } else {
                Result.failure(RuntimeException("Error en la API al subir archivo: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun unsubmitTask(id: Int): Result<StudentTaskItemRemote> {
        return try {
            val response = api.unsubmitTask(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(RuntimeException("Cuerpo de respuesta nulo"))
                }
            } else {
                Result.failure(RuntimeException("Error en la API al anular entrega: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}