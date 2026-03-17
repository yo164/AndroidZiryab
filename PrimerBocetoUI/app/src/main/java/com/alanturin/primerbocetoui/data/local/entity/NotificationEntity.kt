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
    indices = [Index(value = ["idAssistance"], unique = true)]
)data class NotificationEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val idAssistance: Int,
    val idTeacher: Int,
    val status: String,
    val createdAt: String,
    val updatedAt: String?
)


