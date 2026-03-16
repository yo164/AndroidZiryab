package com.alanturin.primerbocetoui.data.remote.model

import kotlinx.serialization.Serializable

/**
 * Respuesta completa del endpoint de login.
 *
 * @property message Mensaje descriptivo del resultado de la operación.
 * @property data Datos del usuario autenticado.
 */
@Serializable
data class LoginResponse(
    val message: String,
    val data: LoginData
)

/**
 * Datos del usuario devueltos tras un login exitoso.
 *
 * @property id Identificador único del usuario en la base de datos.
 * @property email Correo electrónico del usuario.
 * @property name Nombre del usuario.
 * @property role Rol del usuario en el sistema: "TEACHER", "STUDENT" o "ADMIN".
 * @property firebaseUID Identificador único del usuario en Firebase Auth.
 * @property token Token JWT para autenticar peticiones posteriores.
 */
@Serializable
data class LoginData(
    val id: Int,
    val email: String,
    val name: String,
    val role: String,
    val firebaseUID: String,
    val token: String
)
