package com.alanturin.primerbocetoui.data.remote.clasesprofesor

import com.alanturin.primerbocetoui.data.remote.model.AsignaturaListRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ClasesProfesorApi {
    @GET("api/teachers/{id}/subjects")
    suspend fun getAsignaturasProfesor(
        @Path("id") id: Long
    ): Response<AsignaturaListRemote>
}