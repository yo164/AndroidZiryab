package com.alanturin.primerbocetoui.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.domain.repository.AuthRepository
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    /**
     * Rol del usuario autenticado: "TEACHER", "STUDENT" o "ADMIN".
     * */

    val userRole: StateFlow<String?> = sessionViewModel.userRole


    val userId: StateFlow<Int?> = sessionViewModel.userId

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            val result = authRepository.login(email, pass)

            if (result.isSuccess) {
                // CAMBIADO: antes getOrNull() devolvía un String con el rol
                // ahora devuelve LoginData completo con id, role, email...
                val loginData = result.getOrNull()
                if (loginData != null) {
                    // NUEVO: guardamos id y rol en SessionViewModel para que
                    // ClasesProfesorViewModel y otras pantallas puedan acceder al id del usuario
                    sessionViewModel.saveSession(loginData.id, loginData.role, loginData.token)

                }
                _loginSuccess.value = true
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Error al iniciar sesión"
            }
            _isLoading.value = false
        }
    }

    fun logout(){
        sessionViewModel.clearSession()
    }
}
