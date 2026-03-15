package com.alanturin.primerbocetoui.data.remote.teacher

import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote
import com.alanturin.primerbocetoui.data.remote.model.TeacherResponseRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TeacherApi{

@GET("api/teachers/{id}")
suspend fun getTeacherById(
    @Path("id") id: Int
): Response<TeacherResponseRemote>

}