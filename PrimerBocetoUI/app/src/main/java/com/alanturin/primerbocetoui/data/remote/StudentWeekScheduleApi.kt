package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.StudentScheduleListRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentWeekScheduleApi {



    @GET("api/horarios-semanales/student/{idStudent}")
    suspend fun getWeekScheduleByStudent(
        @Path("idStudent") idTeacher: Long
    ): Response<StudentScheduleListRemote>
}