package com.alanturin.primerbocetoui.data.remote


import com.alanturin.primerbocetoui.data.remote.model.SessionClassRemote
import javax.inject.Inject

class ClassSessionsRemoteDataSource @Inject constructor(
    private val api: ClassSessionsApi
) {
    suspend fun getActiveSession(idTeacherAssignment: Int): Result<SessionClassRemote> {
        return try {
            val response = api.getActiveSession(idTeacherAssignment)
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