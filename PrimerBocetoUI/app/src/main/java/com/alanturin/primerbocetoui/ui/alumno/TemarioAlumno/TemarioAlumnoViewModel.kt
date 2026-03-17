package com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.repository.studenttask.StudentTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TemarioAlumnoViewModel @Inject constructor(
    private val repository: StudentTaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<StudentTaskItemRemote>>(emptyList())
    val uiState: StateFlow<List<StudentTaskItemRemote>> = _uiState
    fun loadTasks(enrollmentId: Int) {
        viewModelScope.launch {
            repository.getByStudentEnrollment(enrollmentId).onSuccess { tasks ->
                _uiState.value = tasks
            }.onFailure {
                _uiState.value = emptyList()
            }
        }
    }
}