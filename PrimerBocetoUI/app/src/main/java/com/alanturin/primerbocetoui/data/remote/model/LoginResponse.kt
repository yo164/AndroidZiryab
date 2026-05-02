package com.alanturin.primerbocetoui.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val message: String,
    val data: LoginUserPayload,
    val token: String? = null,
)

@Serializable
data class LoginUserPayload(
    val id: Int,
    val email: String,
    val name: String,
    val role: String,
    val firebaseUID: String,
)

@Serializable
data class LoginData(
    val id: Int,
    val email: String,
    val name: String,
    val role: String,
    val firebaseUID: String,
    val token: String,
)
