package com.alanturin.primerbocetoui.data.remote.announcement

import com.alanturin.primerbocetoui.data.remote.model.AnnouncementItemRemote
import com.alanturin.primerbocetoui.data.remote.model.CreateAnnouncementRequestRemote
import org.json.JSONObject
import javax.inject.Inject

class AnnouncementRemoteDataSourceImpl @Inject constructor(
    private val api: AnnouncementApi
) : AnnouncementRemoteDataSource {

    override suspend fun getAnnouncements(): Result<List<AnnouncementItemRemote>> {
        return try {
            val response = api.getAnnouncements()
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

    override suspend fun createAnnouncement(request: CreateAnnouncementRequestRemote): Result<AnnouncementItemRemote> {
        return try {
            val response = api.createAnnouncement(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body?.data == null) {
                    Result.failure(RuntimeException("Respuesta vacía del servidor"))
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

    private fun mensajeErrorApi(codigo: Int, cuerpo: String?): String {
        val desdeJson = runCatching {
            cuerpo?.let { JSONObject(it).optString("message") }
        }.getOrNull().orEmpty()
        return desdeJson.ifBlank { "Error del servidor: $codigo" }
    }
}
