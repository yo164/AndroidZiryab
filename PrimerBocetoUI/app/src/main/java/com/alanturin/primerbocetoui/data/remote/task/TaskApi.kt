package com.alanturin.primerbocetoui.data.remote.task

import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.CreateTaskResponseRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApi {

    @POST("api/tasks")
    suspend fun createTask(
        @Body request: CreateTaskRequestRemote
    ): Response<CreateTaskResponseRemote>
}