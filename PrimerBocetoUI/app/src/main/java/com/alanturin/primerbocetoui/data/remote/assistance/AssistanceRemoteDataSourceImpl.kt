package com.alanturin.primerbocetoui.data.remote.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistancesBySessionResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.JustifyAssistanceRemoteResponse
import com.alanturin.primerbocetoui.data.remote.model.PatchAssistanceRemoteRequest
import javax.inject.Inject

class AssistanceRemoteDataSourceImpl @Inject constructor(
    private val api: AssistanceApi
): AssistanceRemoteDataSource {
    override suspend fun getPendingJustifications(idTecher: Int): Result<List<AssistanceStudentItemRemote>> {
        return Result.failure(Exception("Recurso no encontrado"))
    }

    override suspend fun getAssistanesbySessionId(id: Int): Result<AssistancesBySessionResponseRemote> {
        return try {
            val response = api.getAssistancebySessionId(id)
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
        }    }

    override suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote> {
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

    override suspend fun justifyAssistanceStatusbyId(id: Int): Result<JustifyAssistanceRemoteResponse> {
        return try {
            val response = api.justifyAssistanceStatus(id)
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

    override suspend fun patchAssistanceStatusbyId(
        id: Int,
        request: PatchAssistanceRemoteRequest
    ): Result<JustifyAssistanceRemoteResponse> {
        return try {
            val response = api.patchAssistanceStatus(id, request)
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