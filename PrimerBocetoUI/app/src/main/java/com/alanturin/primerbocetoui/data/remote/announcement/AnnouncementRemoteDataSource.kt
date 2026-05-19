package com.alanturin.primerbocetoui.data.remote.announcement

import com.alanturin.primerbocetoui.data.remote.model.AnnouncementItemRemote
import com.alanturin.primerbocetoui.data.remote.model.CreateAnnouncementRequestRemote

interface AnnouncementRemoteDataSource {
    suspend fun getAnnouncements(): Result<List<AnnouncementItemRemote>>
    suspend fun createAnnouncement(request: CreateAnnouncementRequestRemote): Result<AnnouncementItemRemote>
}
