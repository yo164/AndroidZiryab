package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import javax.inject.Inject

class StudentTaskRemoteDataSourceImpl @Inject constructor(
    private val api: StudentTaskApi
): StudentTaskRemoteDataSource {
    override suspend fun getByStudentEnrollment(idStudentEnrollment: Int): Result<List<StudentTaskItemRemote>> {
        return try {
            val response = api.getByStudentEnrollment(idStudentEnrollment)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) Result.failure(RuntimeException("Body vacío"))
                else Result.success(body.data)
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}