package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.AlumnoAsignaturaListRemote
import com.alanturin.primerbocetoui.data.remote.model.AsignaturaListRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ClasesAlumnoApi {

    @GET("api/students/{id}/subjects/android")
    suspend fun getClasesAlumno(@Path("id") id: Long): Response<AlumnoAsignaturaListRemote>
}