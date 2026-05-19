package com.alanturin.primerbocetoui.data.remote.announcement

import com.alanturin.primerbocetoui.data.remote.model.AnnouncementListRemote
import com.alanturin.primerbocetoui.data.remote.model.CreateAnnouncementRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.CreateAnnouncementResponseRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AnnouncementApi {
    @GET("api/announcements")
    suspend fun getAnnouncements(): Response<AnnouncementListRemote>

    @POST("api/announcements")
    suspend fun createAnnouncement(
        @Body request: CreateAnnouncementRequestRemote
    ): Response<CreateAnnouncementResponseRemote>
}
