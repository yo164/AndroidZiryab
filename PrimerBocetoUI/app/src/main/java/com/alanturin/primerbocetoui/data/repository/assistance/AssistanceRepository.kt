package com.alanturin.primerbocetoui.data.repository.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.JustifyAssistanceRemoteResponse
import com.alanturin.primerbocetoui.domain.model.AssistanceItem

interface AssistanceRepository {

    suspend fun getAll(userId: Int): Result<List<AssistanceItem>>

    suspend fun deleteAll()

    suspend fun getPendingJustifications(idTeacher: Int): Result<List<AssistanceItem>>

    suspend fun getAssistancesBySessionId(id: Int):Result<List<AssistanceItem>>

    suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote>

    suspend fun getByStudentId(idStudent: Int): Result<List<AssistanceItem>>

    suspend fun justifyAssistancebyId(id: Int): Result<JustifyAssistanceRemoteResponse>

    suspend fun patchAssistancebyId(id: Int, status: String): Result<JustifyAssistanceRemoteResponse>

    suspend fun justifyRequest(id: Int, uri: String):Result<AssistanceItem>
}