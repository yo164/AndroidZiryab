package com.alanturin.primerbocetoui.data.remote.model

import kotlinx.serialization.Serializable

// Añadimos token tras la actualización de login de node
@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val token: String
)
