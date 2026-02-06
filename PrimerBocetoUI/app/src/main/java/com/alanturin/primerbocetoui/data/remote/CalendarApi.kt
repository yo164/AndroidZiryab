package com.alanturin.primerbocetoui.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface CalendarApi {
    @GET
    suspend fun downloadIcs(@Url url: String): ResponseBody
}
