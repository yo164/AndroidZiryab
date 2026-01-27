package com.alanturin.primerbocetoui.ui.alumno

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.Asignatura
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClasesAlumnoViewModel @Inject constructor(
    private val repository: ClasesAlumnoRepository
) : ViewModel() {

    // Estado de la lista
    private val _asignaturas = MutableStateFlow<List<Asignatura>>(emptyList())
    val asignaturas: StateFlow<List<Asignatura>> = _asignaturas.asStateFlow()

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Estado de error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarClases(studentId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val result = repository.getClases(studentId)

            result.onSuccess { lista ->
                _asignaturas.value = lista
            }
            result.onFailure { exception ->
                _error.value = exception.message ?: "Error desconocido"
            }

            _isLoading.value = false
        }
    }
}