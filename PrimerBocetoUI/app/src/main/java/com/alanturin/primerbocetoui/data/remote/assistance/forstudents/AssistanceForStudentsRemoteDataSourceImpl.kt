package com.alanturin.primerbocetoui.data.remote.assistance.forstudents

import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentResponseRemote
import javax.inject.Inject

class AssistanceForStudentsRemoteDataSourceImpl @Inject constructor(
    private val api: AssistanceForStudentsApi
) : AssistanceForStudentsRemoteDataSource {

    override suspend fun getByStudentId(idStudent: Int): Result<List<AssistanceStudentItemRemote>> {
        return try {
            val response = api.getByStudentId(idStudent)
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