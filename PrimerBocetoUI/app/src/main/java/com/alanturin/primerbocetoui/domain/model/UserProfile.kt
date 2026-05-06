package com.alanturin.primerbocetoui.domain.model

import com.alanturin.primerbocetoui.data.remote.model.UserProfileRemote

data class UserProfile(
    val id: Int,
    val name: String,
    val email: String,
    val role: String
)

/** Resultado de actualizar perfil: perfil y JWT nuevo solo si el backend lo devuelve (cambio de email). */
data class UpdatedProfile(
    val profile: UserProfile,
    val newJwt: String?
)

fun UserProfileRemote.toDomain() = UserProfile(
    id = id,
    name = name,
    email = email,
    role = role
)
