package com.alanturin.primerbocetoui.domain.model

import com.alanturin.primerbocetoui.data.local.entity.NotificationEntity

data class NotificationItem(
    val id: Int,
    val idAssistance: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String?,
    val idStudent: Int?,
    val idSession: Int?,
    val idStudentEnrollment: Int?,
    val idTeacher: Int?,
    val assistanceStatus: String?,
    val subjectName: String?,
    val date: String?,
    val startTime: String?,
    val uri: String?
)

data class NotificationInsertEntity(
    val id: Int,
    val idAssistance: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String?,
)
fun NotificationInsertEntity.toEntity() = NotificationEntity(
    idAssistance = this.idAssistance,
    status = this.status,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

