package com.alanturin.primerbocetoui.ui.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Estado de sesión compartido (id, rol, JWT y datos para la cabecera).
 */
@Singleton
class SessionViewModel @Inject constructor(
    private val assingmentSession: AssignmentSessionService
) {

    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId.asStateFlow()

    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    private val _userEmail = MutableStateFlow("")
    val userEmail: StateFlow<String> = _userEmail.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    fun saveSession(id: Int, role: String, email: String, name: String, token: String) {
        _userId.value = id
        _userRole.value = role
        _token.value = token
        _userEmail.value = email
        _userName.value = name
    }

    /** Tras editar la ficha: nombre/email en cabecera; JWT solo si el servidor lo renueva (email nuevo). */
    fun aplicarCambioPerfil(email: String, name: String, nuevoJwt: String?) {
        _userEmail.value = email
        _userName.value = name
        if (nuevoJwt != null) {
            _token.value = nuevoJwt
        }
    }

    fun clearSession() {
        _userId.value = null
        _userRole.value = null
        _token.value = null
        _userEmail.value = ""
        _userName.value = ""
        assingmentSession.clearCurrentAssignment()
    }
}
