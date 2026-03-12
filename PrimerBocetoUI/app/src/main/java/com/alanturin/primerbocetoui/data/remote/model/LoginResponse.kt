package com.alanturin.primerbocetoui.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val data: LoginUserData
)

@Serializable
data class LoginUserData(
    val id: Long,
    val email: String,
    val name: String,
    val role: String,
    val firebaseUID: String,
    val token: String
)
