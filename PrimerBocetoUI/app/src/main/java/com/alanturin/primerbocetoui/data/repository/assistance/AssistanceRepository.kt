package com.alanturin.primerbocetoui.data.repository.assistance

import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote

interface AssistanceRepository {
    suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote>

}