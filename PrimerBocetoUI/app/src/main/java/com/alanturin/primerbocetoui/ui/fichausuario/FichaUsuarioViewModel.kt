package com.alanturin.primerbocetoui.ui.alumno.ficha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.data.repository.teacher.TeacherRepository
import com.alanturin.primerbocetoui.domain.model.AssistanceItem
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FichaUsuarioViewModel @Inject constructor(
    private val repository: AssistanceRepository,
    private val sessionViewModel: SessionViewModel,
    private val teacherRepository: TeacherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun cargarFaltas() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val idUser = sessionViewModel.userId.value
            val userRole = sessionViewModel.userRole.value

            if (idUser == null || userRole == null) {
                _uiState.value = UiState.Error("No hay sesión activa")
                return@launch
            }

            when (userRole) {
                "STUDENT" -> {
                    val result = repository.getByStudentId(idUser)
                    result.onSuccess { lista ->
                        _uiState.value = if (lista.isEmpty()) UiState.Empty else UiState.Success(lista)
                    }.onFailure {
                        _uiState.value = UiState.Error("Error al cargar faltas")
                    }
                }
                "TEACHER" -> {
                    val result = teacherRepository.getTeacherById(idUser)
                    result.onSuccess { teacher ->
                        _uiState.value = UiState.TeacherSuccess(teacher)
                    }.onFailure {
                        _uiState.value = UiState.Error("Error al cargar datos del profesor")
                    }                }
            }


        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Empty : UiState()
        data class Success(val faltas: List<AssistanceItem>) : UiState()
        data class Error(val message: String) : UiState()
        data class TeacherSuccess(val teacher: TeacherRemote) : UiState() // ← aquí

    }
}