package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.alanturin.primerbocetoui.domain.model.NotificationItem

@Entity(
    tableName = "notification",
    foreignKeys = [
        ForeignKey(
            entity = AssistanceEntity::class,
            parentColumns = ["id"],
            childColumns = ["idAssistance"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["idAssistance"])]
)data class NotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idAssistanceEntity: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String?
)

fun NotificationEntity.toDomain() = NotificationItem(
    id = this.id,
    idAssistance = this.idAssistanceEntity,
    status = this.status,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

