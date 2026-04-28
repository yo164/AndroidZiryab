package com.alanturin.primerbocetoui.domain.model

import com.alanturin.primerbocetoui.data.remote.model.UserProfileRemote

data class UserProfile(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)

fun UserProfileRemote.toDomain() = UserProfile(
    id = id,
    name = name,
    email = email,
    role = role
)
