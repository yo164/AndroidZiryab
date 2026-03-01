package com.alanturin.primerbocetoui.ui.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton que gestiona los datos de la asignatura activa.
 * Se actualiza cada vez que el profesor selecciona una asignatura.
 */
@Singleton
class AssignmentSessionService @Inject constructor() {

    /** ID de la asignatura activa. */
    private val _currentSubjectId = MutableStateFlow<Int?>(null)
    val currentSubjectId: StateFlow<Int?> = _currentSubjectId.asStateFlow()

    /** ID del grupo activo. */
    private val _currentGroupId = MutableStateFlow<Int?>(null)
    val currentGroupId: StateFlow<Int?> = _currentGroupId.asStateFlow()

    /**
     * Guarda los datos de la asignatura activa cuando el profesor pulsa Gestionar.
     *
     * @param idSubject ID de la asignatura seleccionada.
     * @param idGroup ID del grupo seleccionado.
     */

    /** ID del assignment (TeacherOnSubjectOnGroup) activo. */
    private val _currentAssignmentId = MutableStateFlow<Long?>(null)
    val currentAssignmentId: StateFlow<Long?> = _currentAssignmentId.asStateFlow()
    fun saveCurrentAssignment(idSubject: Int, idGroup: Int, idAssignment: Long) {
        _currentSubjectId.value = idSubject
        _currentGroupId.value = idGroup
        _currentAssignmentId.value = idAssignment
    }

    /**
     * Limpia los datos de la asignatura activa.
     */
    fun clearCurrentAssignment() {
        _currentSubjectId.value = null
        _currentGroupId.value = null
        _currentAssignmentId.value = null
    }
}