package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grupo")
data class GroupEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val capacity: Int?,
    val createdAt: String
)