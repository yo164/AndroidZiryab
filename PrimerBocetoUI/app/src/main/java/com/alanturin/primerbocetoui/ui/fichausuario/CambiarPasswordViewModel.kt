package com.alanturin.primerbocetoui.ui.fichausuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CambiarPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun cambiarPassword(currentPassword: String, newPassword: String, confirmPassword: String) {
        if (newPassword != confirmPassword) {
            _uiState.value = UiState.NoMatch
            return
        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            userRepository.changePassword(currentPassword, newPassword, confirmPassword)
                .onSuccess { _uiState.value = UiState.Success }
                .onFailure { e ->
                    _uiState.value = UiState.Error(e.message ?: "Error")
                }
        }
    }

    sealed class UiState {
        data object Idle : UiState()
        data object Loading : UiState()
        data object Success : UiState()
        data object NoMatch : UiState()
        data class Error(val message: String) : UiState()
    }
}
