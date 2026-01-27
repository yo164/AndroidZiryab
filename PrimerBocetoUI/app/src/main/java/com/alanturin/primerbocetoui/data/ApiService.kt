package com.alanturin.primerbocetoui.data

import com.alanturin.primerbocetoui.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("teachers/{id}/subjects")
    suspend fun getSubjectsByTeachers(@Path("id") teacherId:Int): ApiResponse
}

