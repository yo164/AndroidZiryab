package com.alanturin.primerbocetoui.data.repository.assistance

import com.alanturin.primerbocetoui.data.remote.assistance.AssistanceRemoteDataSourceImpl
import com.alanturin.primerbocetoui.data.remote.assistance.forstudents.AssistanceForStudentsRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistancesBySessionResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.JustifyAssistanceRemoteResponse
import com.alanturin.primerbocetoui.data.remote.model.PatchAssistanceRemoteRequest
import javax.inject.Inject

class AssistanceRepositoryImpl @Inject constructor(
    private val remoteDataSource: AssistanceRemoteDataSourceImpl,
    private val forStudentsRemote: AssistanceForStudentsRemoteDataSource
) : AssistanceRepository {
    override suspend fun getPendingJustifications(idTeacher: Int): Result<List<AssistanceStudentItemRemote>> {
        val remoteResult = remoteDataSource.getPendingJustifications(idTeacher)

        return if (remoteResult.isSuccess) {
            remoteResult
        } else {
            // TODO: aqui ira la llamada a local datasource (Room)
            Result.failure(remoteResult.exceptionOrNull() ?: RuntimeException("Error desconocido"))
        }
    }

    override suspend fun getAssistancesBySessionId(id: Int): Result<AssistancesBySessionResponseRemote> {
        return remoteDataSource.getAssistanesbySessionId(id)
    }

    override suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote> {
        return remoteDataSource.createBulk(request)
    }

    override suspend fun getByStudentId(idStudent: Int): Result<List<AssistanceStudentItemRemote>> {
        return forStudentsRemote.getByStudentId(idStudent)
    }

    override suspend fun justifyAssistancebyId(id: Int): Result<JustifyAssistanceRemoteResponse> {
        return remoteDataSource.justifyAssistanceStatusbyId(id)
    }

    override suspend fun patchAssistancebyId(id: Int, status: String): Result<JustifyAssistanceRemoteResponse> {
        return remoteDataSource.patchAssistanceStatusbyId(id, request = PatchAssistanceRemoteRequest(status = status))
    }
}