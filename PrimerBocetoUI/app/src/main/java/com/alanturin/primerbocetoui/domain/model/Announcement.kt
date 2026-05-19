package com.alanturin.primerbocetoui.domain.model

data class Announcement(
    val id: Int,
    val title: String,
    val body: String,
    val createdAt: String,
    val createdByUserId: Int,
    val creatorName: String?
)
