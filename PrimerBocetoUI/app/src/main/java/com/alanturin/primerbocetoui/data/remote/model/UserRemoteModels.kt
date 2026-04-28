package com.alanturin.primerbocetoui.data.remote.model

data class UserProfileRemote(
    val id: Int,
    val email: String,
    val name: String,
    val surname: String?,
    val ndSurname: String?,
    val role: String
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
