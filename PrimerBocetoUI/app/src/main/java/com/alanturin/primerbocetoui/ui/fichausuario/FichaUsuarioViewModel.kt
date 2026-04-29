package com.alanturin.primerbocetoui.ui.alumno.ficha

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote
import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote
import com.alanturin.primerbocetoui.data.repository.assistance.AssistanceRepository
import com.alanturin.primerbocetoui.data.repository.teacher.TeacherRepository
import com.alanturin.primerbocetoui.data.repository.user.UserRepository
import com.alanturin.primerbocetoui.domain.model.AssistanceItem
import com.alanturin.primerbocetoui.domain.model.UserProfile
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
    private val teacherRepository: TeacherRepository,
    private val userRepository: UserRepository
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
                    val profileResult = userRepository.getProfile()
                    val faltasResult = repository.getByStudentId(idUser)

                    profileResult.onSuccess { profile ->
                        faltasResult.onSuccess { lista ->
                            _uiState.value = if (lista.isEmpty()) {
                                UiState.Empty(profile)
                            } else {
                                UiState.Success(lista, profile)
                            }
                        }.onFailure {
                            _uiState.value = UiState.Error("Error al cargar faltas")
                        }
                    }.onFailure {
                        _uiState.value = UiState.Error("Error al cargar perfil")
                    }
                }
                "TEACHER" -> {
                    val profileResult = userRepository.getProfile()
                    val teacherResult = teacherRepository.getTeacherById(idUser)

                    profileResult.onSuccess { profile ->
                        teacherResult.onSuccess { teacher ->
                            _uiState.value = UiState.TeacherSuccess(teacher, profile)
                        }.onFailure {
                            _uiState.value = UiState.Error("Error al cargar datos del profesor")
                        }
                    }.onFailure {
                        _uiState.value = UiState.Error("Error al cargar perfil")
                    }
                }
            }


        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Empty(val profile: UserProfile) : UiState()
        data class Success(val faltas: List<AssistanceItem>, val profile: UserProfile) : UiState()
        data class Error(val message: String) : UiState()
        data class TeacherSuccess(val teacher: TeacherRemote, val profile: UserProfile) : UiState()

    }
}