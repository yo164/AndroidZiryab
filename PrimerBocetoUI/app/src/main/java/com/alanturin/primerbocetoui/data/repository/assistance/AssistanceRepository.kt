package com.alanturin.primerbocetoui.data.repository.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistancesBySessionResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.JustifyAssistanceRemoteResponse
import com.alanturin.primerbocetoui.data.remote.model.PatchAssistanceRemoteRequest

interface AssistanceRepository {

    suspend fun getPendingJustifications(idTeacher: Int): Result<List<AssistanceStudentItemRemote>>

    suspend fun getAssistancesBySessionId(id: Int):Result<AssistancesBySessionResponseRemote>

    suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote>

    suspend fun getByStudentId(idStudent: Int): Result<List<AssistanceStudentItemRemote>>

    suspend fun justifyAssistancebyId(id: Int): Result<JustifyAssistanceRemoteResponse>

    suspend fun patchAssistancebyId(id: Int, status: String): Result<JustifyAssistanceRemoteResponse>
}