package com.alanturin.primerbocetoui.data.remote.model


data class ActiveSessionResponseRemote(
    val success: Boolean,
    val data: SessionClassRemote
)

data class SessionClassRemote(
    val id: Int,
    val idSchedule: Int,
    val date: String,
    val status: String,
    val apointments: String?,
    val createdAt: String
)
