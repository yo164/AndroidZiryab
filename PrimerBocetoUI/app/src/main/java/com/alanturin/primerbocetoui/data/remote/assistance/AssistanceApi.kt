package com.alanturin.primerbocetoui.data.remote.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistancesBySessionResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.JustifyAssistanceRemoteResponse
import com.alanturin.primerbocetoui.data.remote.model.PatchAssistanceRemoteRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface AssistanceApi {

    @GET("api/assistances")
    suspend fun getAll(): Response<AssistancesBySessionResponseRemote>

    @GET("api/assistances/session/{id}")
    suspend fun getAssistancebySessionId(
        @Path("id") id: Int
    ):Response<AssistancesBySessionResponseRemote>

    @POST("api/assistances/bulk")
    suspend fun createBulk(
        @Body request: AssistanceBulkRequestRemote
    ): Response<AssistanceBulkResponseRemote>


    /**
     * @PATCH
     * actualiza el estado de una asistencia concreta
     * a justificado
     */
    @PATCH("api/assistances/{id}")
    suspend fun justifyAssistanceStatus(
        @Path("id") id: Int
    ): Response<JustifyAssistanceRemoteResponse>

    /**
     * @PATCH
     * actualiza el estado de una asistencia a cualquier
     * otro valor
     */
    @PATCH("api/assistances/assistancestatus/{id}")
    suspend fun patchAssistanceStatus(
        @Path("id") id: Int,
        @Body request: PatchAssistanceRemoteRequest
    ): Response<JustifyAssistanceRemoteResponse>


}