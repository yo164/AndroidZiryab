package com.alanturin.primerbocetoui.data.remote.task

import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.CreateTaskResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApi {
    @POST("api/tasks")
    suspend fun createTask(
        @Body request: CreateTaskRequestRemote
    ): Response<CreateTaskResponseRemote>

    @GET("api/tasks/teacher-assignment/{idTeacherAssignment}")
    suspend fun getTasksByTeacherAssignment(
        @Path("idTeacherAssignment") idTeacherAssignment: Long
    ): Response<List<TaskItemRemote>>

    @GET("api/tasks/{id}")
    suspend fun getTaskById(
        @Path("id") id: Int
    ): Response<TaskItemRemote>
}