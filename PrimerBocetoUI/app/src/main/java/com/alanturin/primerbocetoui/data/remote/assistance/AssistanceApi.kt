package com.alanturin.primerbocetoui.data.remote.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AssistanceApi {
    @POST("api/assistances/bulk")
    suspend fun createBulk(
        @Body request: AssistanceBulkRequestRemote
    ): Response<AssistanceBulkResponseRemote>
}