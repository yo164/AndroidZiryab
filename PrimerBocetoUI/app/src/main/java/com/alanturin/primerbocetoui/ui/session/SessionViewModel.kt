package com.alanturin.primerbocetoui.ui.session

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ViewModel compartido a nivel de actividad.
 * Gestiona el estado de sesión del usuario autenticado.
 * Es la fuente de verdad para los datos del usuario logueado.
 */
@Singleton
class SessionViewModel @Inject constructor(
    private val assingmentSession: AssignmentSessionService
)  {

    /** ID del usuario autenticado. Null si no hay sesión activa. */
    private val _userId = MutableStateFlow<Int?>(null)
    val userId: StateFlow<Int?> = _userId.asStateFlow()

    /** Rol del usuario autenticado: "TEACHER", "STUDENT" o "ADMIN". Null si no hay sesión activa. */
    private val _userRole = MutableStateFlow<String?>(null)
    val userRole: StateFlow<String?> = _userRole.asStateFlow()

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token.asStateFlow()

    /**
     * Guarda los datos de sesión tras un login exitoso.
     *
     * @param id Identificador único del usuario en la base de datos.
     * @param role Rol del usuario: "TEACHER", "STUDENT" o "ADMIN".
     */
    fun saveSession(id: Int, role: String, token: String) {
        _userId.value = id
        _userRole.value = role
        _token.value = token
    }

    /**
     * Limpia los datos de sesión al hacer logout.
     */
    fun clearSession() {
        _userId.value = null
        _userRole.value = null
        _token.value = null
        assingmentSession.clearCurrentAssignment()
    }
}