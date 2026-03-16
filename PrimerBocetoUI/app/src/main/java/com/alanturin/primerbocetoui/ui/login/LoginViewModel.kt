package com.alanturin.primerbocetoui.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.initialdata.InitialDataController
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
    private val sessionViewModel: SessionViewModel,
    private val initialDataController: InitialDataController
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
                val loginData = result.getOrNull()
                if (loginData != null) {
                    sessionViewModel.saveSession(loginData.id, loginData.role, loginData.token)
                    launch { initialDataController.cargarDatosIniciales() }
                    if (loginData.role == "TEACHER"){
                        launch { initialDataController.programarWorker(loginData.id) }
                    }
                }
                _loginSuccess.value = true
            } else {
                _error.value = result.exceptionOrNull()?.message ?: "Error al iniciar sesión"
            }
            _isLoading.value = false
        }
    }

    fun logout(){
        viewModelScope.launch {
            initialDataController.limpiarDatos()
        }
        sessionViewModel.clearSession()
    }
}
