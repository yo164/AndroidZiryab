package com.alanturin.primerbocetoui.data.remote.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import javax.inject.Inject

class AssistanceRemoteDataSource @Inject constructor(
    private val api: AssistanceApi
) {
    suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote> {
        return try {
            val response = api.createBulk(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    Result.failure(RuntimeException("Body vacío"))
                } else {
                    Result.success(body)
                }
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}