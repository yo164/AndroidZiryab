package com.alanturin.primerbocetoui.data.repository.announcement

import com.alanturin.primerbocetoui.domain.model.Announcement

interface AnnouncementRepository {
    suspend fun getAnnouncements(): Result<List<Announcement>>
    suspend fun createAnnouncement(title: String, body: String): Result<Announcement>
}
