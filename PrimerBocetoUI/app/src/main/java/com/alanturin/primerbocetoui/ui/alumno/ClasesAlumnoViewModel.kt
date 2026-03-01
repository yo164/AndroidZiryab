package com.alanturin.primerbocetoui.ui.alumno

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.domain.model.Asignatura
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClasesAlumnoViewModel @Inject constructor(
    private val repository: ClasesAlumnoRepository,
    private val sessionViewModel: SessionViewModel
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

    fun cargarClases() {
        if (_asignaturas.value.isNotEmpty()) {
            return
        }
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            val studentId = sessionViewModel.userId.value

            if (studentId != null) {
                val result = repository.getClases(studentId.toLong())
                result.onSuccess { lista ->
                    _asignaturas.value = lista
                }
                result.onFailure { exception ->
                    _error.value = exception.message ?: "Error desconocido"
                }
            } else {
                _error.value = "No hay sesión activa"
            }
            _isLoading.value = false
        }
    }

    fun recargarClases(studentId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            val studentId = sessionViewModel.userId.value

            if (studentId != null) {
                val result = repository.getClases(studentId.toLong())
                result.onSuccess { lista ->
                    _asignaturas.value = lista
                }
                result.onFailure { exception ->
                    _error.value = "Error al actualizar: ${exception.message}"
                }
            } else {
                _error.value = "No hay sesión activa"
            }
            _isLoading.value = false
        }
    }

}