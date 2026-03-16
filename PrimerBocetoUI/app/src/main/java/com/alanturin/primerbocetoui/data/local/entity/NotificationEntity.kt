package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

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
    @PrimaryKey
    val id: Int,


    val idAssistanceEntity: AssistanceEntity
)