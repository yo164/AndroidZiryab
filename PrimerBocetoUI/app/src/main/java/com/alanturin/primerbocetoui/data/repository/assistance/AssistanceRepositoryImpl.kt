package com.alanturin.primerbocetoui.data.repository.assistance

import com.alanturin.primerbocetoui.data.remote.assistance.AssistanceRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import javax.inject.Inject

class AssistanceRepositoryImpl @Inject constructor(
    private val remoteDataSource: AssistanceRemoteDataSource
) : AssistanceRepository {
    override suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote> {
        return remoteDataSource.createBulk(request)
    }
}