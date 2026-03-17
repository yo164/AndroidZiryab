package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskListResponseRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentTaskApi {
    @GET("api/student-tasks/student/{idStudentEnrollment}")
    suspend fun getByStudentEnrollment(
        @Path("idStudentEnrollment") idStudentEnrollment: Int
    ): Response<StudentTaskListResponseRemote>
}