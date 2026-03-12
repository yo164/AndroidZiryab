package com.alanturin.primerbocetoui.data.remote.assistance.forstudents

import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentResponseRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface AssistanceForStudentsApi {

    @GET("api/assistances/student/{idStudent}")
    suspend fun getByStudentId(
        @Path("idStudent") idStudent: Int
    ): Response<AssistanceStudentResponseRemote>


}