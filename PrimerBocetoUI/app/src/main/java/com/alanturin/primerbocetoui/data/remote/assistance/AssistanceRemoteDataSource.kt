package com.alanturin.primerbocetoui.data.remote.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistancesBySessionResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.JustifyAssistanceRemoteResponse
import com.alanturin.primerbocetoui.data.remote.model.PatchAssistanceRemoteRequest

interface AssistanceRemoteDataSource {


    suspend fun getAssistanesbySessionId(id: Int): Result<AssistancesBySessionResponseRemote>

    suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote>

    suspend fun justifyAssistanceStatusbyId(id: Int): Result<JustifyAssistanceRemoteResponse>

    suspend fun patchAssistanceStatusbyId(id:Int, request: PatchAssistanceRemoteRequest): Result<JustifyAssistanceRemoteResponse>
}