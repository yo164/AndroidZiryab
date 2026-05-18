package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskListResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.UploadFileResponseRemote
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface StudentTaskApi {
    @GET("api/student-tasks/student/{idStudentEnrollment}")
    suspend fun getByStudentEnrollment(
        @Path("idStudentEnrollment") idStudentEnrollment: Int
    ): Response<StudentTaskListResponseRemote>

    @PUT("api/student-tasks/{id}/submit")
    suspend fun submitTask(
        @Path("id") id: Int,
        @Body request: SubmitTaskRequestRemote
    ): Response<StudentTaskResponseRemote>

    @GET("api/student-tasks/task/{taskId}")
    suspend fun getSubmissionsByTask(
        @Path("taskId") taskId: Int
    ): Response<StudentTaskListResponseRemote>

    @Multipart
    @POST("api/student-tasks/upload-submission")
    suspend fun uploadFile(
        @Part file: okhttp3.MultipartBody.Part
    ): Response<UploadFileResponseRemote>

    @DELETE("api/student-tasks/{id}/submit")
    suspend fun unsubmitTask(
        @Path("id") id: Int
    ): Response<StudentTaskResponseRemote>
}