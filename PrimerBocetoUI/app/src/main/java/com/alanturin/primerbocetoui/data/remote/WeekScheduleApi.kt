package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleListRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WeekScheduleApi {
    @GET("api/horarios-semanales/teacher-assignment/{idTeacherAssignment}")
    suspend fun getWeekScheduleByAssignment(
        @Path("idTeacherAssignment") idTeacherAssignment: Long
    ): Response<WeekScheduleListRemote>
}