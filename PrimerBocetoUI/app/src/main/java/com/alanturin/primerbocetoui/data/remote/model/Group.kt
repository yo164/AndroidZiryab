package com.alanturin.primerbocetoui.data.remote.model

data class Group(
    val id: Int,
    val name: String,
    val capacity: Int?,
    val createdAt: String
)

data class GroupResponse(
    val success: Boolean,
    val data: List<Group>,
    val count: Int
)