package com.alanturin.primerbocetoui.data.remote.model

import com.alanturin.primerbocetoui.domain.model.Announcement

data class CreateAnnouncementRequestRemote(
    val title: String,
    val body: String
)

data class CreateAnnouncementResponseRemote(
    val success: Boolean,
    val message: String?,
    val data: AnnouncementItemRemote?
)

data class AnnouncementListRemote(
    val success: Boolean,
    val message: String?,
    val data: List<AnnouncementItemRemote>?
)

data class AnnouncementItemRemote(
    val id: Int,
    val title: String,
    val body: String,
    val createdAt: String,
    val createdByUserId: Int,
    val creator: CreatorRemote?
)

data class CreatorRemote(
    val id: Int,
    val name: String,
    val surname: String?,
    val email: String
)

fun AnnouncementItemRemote.toDomain(): Announcement = Announcement(
    id = id,
    title = title,
    body = body,
    createdAt = createdAt,
    createdByUserId = createdByUserId,
    creatorName = creator?.let { 
        val fullname = "${it.name} ${it.surname.orEmpty()}".trim()
        if (fullname.isNotBlank()) fullname else null
    }
)
