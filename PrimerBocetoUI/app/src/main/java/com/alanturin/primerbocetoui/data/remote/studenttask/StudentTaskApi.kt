package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.GradeSubmissionRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskListResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface StudentTaskApi {
    @GET("api/student-tasks/student/{idStudentEnrollment}")
    suspend fun getByStudentEnrollment(
        @Path("idStudentEnrollment") idStudentEnrollment: Int
    ): Response<StudentTaskListResponseRemote>

    @POST("api/student-tasks/submit")
    suspend fun submitTask(
        @Body request: SubmitTaskRequestRemote
    ): Response<StudentTaskResponseRemote>

    @GET("api/student-tasks/task/{taskId}")
    suspend fun getSubmissionsByTask(
        @Path("taskId") taskId: Int
    ): Response<StudentTaskListResponseRemote>

    @PUT("api/student-tasks/{id}/grade")
    suspend fun gradeSubmission(
        @Path("id") id: Int,
        @Body request: GradeSubmissionRequestRemote
    ): Response<StudentTaskResponseRemote>
}