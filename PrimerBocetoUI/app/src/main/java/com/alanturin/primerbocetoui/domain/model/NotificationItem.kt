package com.alanturin.primerbocetoui.domain.model

import com.alanturin.primerbocetoui.data.local.entity.NotificationEntity

data class NotificationItem(
    val id: Int,
    val idAssistance: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String?
)
fun NotificationItem.toEntity() = NotificationEntity(
    id = this.id,
    idAssistanceEntity = this.idAssistance,
    status = this.status,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)