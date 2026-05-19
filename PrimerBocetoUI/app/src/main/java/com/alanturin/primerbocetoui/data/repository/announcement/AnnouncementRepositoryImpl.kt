package com.alanturin.primerbocetoui.data.repository.announcement

import com.alanturin.primerbocetoui.data.remote.announcement.AnnouncementRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.CreateAnnouncementRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.toDomain
import com.alanturin.primerbocetoui.domain.model.Announcement
import javax.inject.Inject

class AnnouncementRepositoryImpl @Inject constructor(
    private val remoteDataSource: AnnouncementRemoteDataSource
) : AnnouncementRepository {

    override suspend fun getAnnouncements(): Result<List<Announcement>> {
        return remoteDataSource.getAnnouncements().map { remoteList ->
            remoteList.map { it.toDomain() }
        }
    }

    override suspend fun createAnnouncement(title: String, body: String): Result<Announcement> {
        val request = CreateAnnouncementRequestRemote(title = title, body = body)
        return remoteDataSource.createAnnouncement(request).map { it.toDomain() }
    }
}
