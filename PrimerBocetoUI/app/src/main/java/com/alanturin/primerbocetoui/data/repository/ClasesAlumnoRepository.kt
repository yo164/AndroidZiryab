package com.alanturin.primerbocetoui.domain.repository

import com.alanturin.primerbocetoui.domain.model.Asignatura


interface ClasesAlumnoRepository {
    suspend fun getClases(studentId: Long): Result<List<Asignatura>>
}