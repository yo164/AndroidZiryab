package com.alanturin.primerbocetoui.data.remote.teacher

import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote
import javax.inject.Inject

class TeacherRemoteDataSourceImpl @Inject constructor(
    private val api: TeacherApi
): TeacherRemoteDataSource {
    override suspend fun getTeacherById(id: Int): Result<TeacherRemote> {
        return try {
            val response = api.getTeacherById(id)
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