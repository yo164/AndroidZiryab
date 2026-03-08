package com.alanturin.primerbocetoui.data.repository.assistance

import com.alanturin.primerbocetoui.data.remote.assistance.AssistanceRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.assistance.forstudents.AssistanceForStudentsRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import javax.inject.Inject

class AssistanceRepositoryImpl @Inject constructor(
    private val remoteDataSource: AssistanceRemoteDataSource,
    private val forStudentsRemote: AssistanceForStudentsRemoteDataSource
) : AssistanceRepository {
    override suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote> {
        return remoteDataSource.createBulk(request)
    }

    override suspend fun getByStudentId(idStudent: Int): Result<List<AssistanceStudentItemRemote>> {
        return forStudentsRemote.getByStudentId(idStudent)
    }
}