package com.alanturin.primerbocetoui.ui.alumno.ficha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FichaUsuarioViewModel @Inject constructor(
    private val repository: AssistanceRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun cargarFaltas() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val idStudent = sessionViewModel.userId.value

            if (idStudent == null) {
                _uiState.value = UiState.Error("No hay sesión activa")
                return@launch
            }

            val result = repository.getByStudentId(idStudent)

            result.onSuccess { lista ->
                _uiState.value = if (lista.isEmpty()) UiState.Empty else UiState.Success(lista)
            }.onFailure {
                _uiState.value = UiState.Error("Error al cargar faltas")
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val faltas: List<AssistanceStudentItemRemote>) : UiState()
        data class Error(val message: String) : UiState()
    }
}