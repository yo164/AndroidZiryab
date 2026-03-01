package com.alanturin.primerbocetoui.ui.profesor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.Asignatura
import com.alanturin.primerbocetoui.data.repository.ClasesProfesorRepository
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClasesProfesorViewModel @Inject constructor(
    private val repository: ClasesProfesorRepository,
    private val sessionViewModel: SessionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    // Simulamos el ngOnInit de Angular
    fun cargarClases() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val profesorId = sessionViewModel.userId.value

            if (profesorId != null) {
                val result = repository.readAll(profesorId.toLong())

                result.onSuccess { lista ->
                    if (lista.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(lista)
                    }
                }.onFailure {
                    _uiState.value = UiState.Error("Error al cargar asignaturas")
                }
            } else {
                _uiState.value = UiState.Error("No hay sesión activa")
            }
        }
    }

    // Estados de la vista (Loading, Error, Success) como en tu HTML @if
    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val asignaturas: List<Asignatura>) : UiState()
        data class Error(val message: String) : UiState()
    }
}