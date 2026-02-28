package com.alanturin.primerbocetoui.ui.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.Group
import com.alanturin.primerbocetoui.data.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val repository : GroupRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun cargaGrupos(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = repository.getGroups()

            if (result.isEmpty()) {
                _uiState.value = UiState.Empty
            } else {
                _uiState.value = UiState.Success(result)
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val groups: List<Group>) : UiState()
        data class Error(val message: String) : UiState()
    }
}


/*
*  private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    // Simulamos el ngOnInit de Angular
    fun cargarClases(profesorId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = repository.readAll(profesorId)

            result.onSuccess { lista ->
                if (lista.isEmpty()) {
                    _uiState.value = UiState.Empty
                } else {
                    _uiState.value = UiState.Success(lista)
                }
            }.onFailure {
                _uiState.value = UiState.Error("Error al cargar asignaturas")
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
* */