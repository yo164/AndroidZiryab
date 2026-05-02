package com.alanturin.primerbocetoui.ui.fichausuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.repository.user.UserRepository
import com.alanturin.primerbocetoui.domain.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditarFichaViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _perfil = MutableStateFlow<UserProfile?>(null)
    val perfil: StateFlow<UserProfile?> = _perfil.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.getProfile()
                .onSuccess { _perfil.value = it }
                .onFailure {
                    _perfil.value = UserProfile(id = 0, name = "", email = "", role = "")
                }
        }
    }

    fun guardarPerfil(name: String, email: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            userRepository.updateProfile(name, email)
                .onSuccess { actualizado ->
                    _perfil.value = actualizado
                    _uiState.value = UiState.Success
                }
                .onFailure { e ->
                    _uiState.value = UiState.Error(e.message ?: "Error")
                }
        }
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}
