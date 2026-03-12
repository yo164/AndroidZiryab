package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.ActiveSessionResponseRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ClassSessionsApi {
    @GET("api/sessions/active")
    suspend fun getActiveSession(
        @Query("idTeacherAssignment") idTeacherAssignment: Int
    ): Response<ActiveSessionResponseRemote>
}