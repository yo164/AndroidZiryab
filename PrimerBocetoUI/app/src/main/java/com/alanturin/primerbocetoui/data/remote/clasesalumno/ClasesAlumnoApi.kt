package com.alanturin.primerbocetoui.data.remote.clasesalumno

import com.alanturin.primerbocetoui.data.remote.model.AlumnoAsignaturaListRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ClasesAlumnoApi {

    @GET("api/students/{id}/subjects")
    suspend fun getClasesAlumno(@Path("id") id: Long): Response<AlumnoAsignaturaListRemote>
}