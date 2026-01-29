package com.alanturin.primerbocetoui.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val firebaseUID: String
)
