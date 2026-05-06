package com.alanturin.primerbocetoui.data.remote.model

import com.google.gson.annotations.SerializedName

data class UserProfileRemote(
    val id: Int,
    val email: String,
    val name: String,
    val surname: String?,
    val ndSurname: String?,
    val role: String
)

/** Respuesta de PATCH /api/users/me (incluye token nuevo si cambió el email). */
data class UpdateProfileResponse(
    val message: String? = null,
    @SerializedName("data") val user: UserProfileRemote,
    val token: String? = null
)

data class UpdateProfileRequest(
    val name: String,
    val email: String
)

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
