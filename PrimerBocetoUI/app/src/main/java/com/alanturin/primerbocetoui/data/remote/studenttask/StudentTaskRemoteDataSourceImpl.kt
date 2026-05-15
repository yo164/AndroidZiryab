package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.GradeSubmissionRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
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

    override suspend fun submitTask(request: SubmitTaskRequestRemote): Result<StudentTaskItemRemote> {
        return try {
            val response = api.submitTask(request)
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
}