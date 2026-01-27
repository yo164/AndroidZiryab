package com.alanturin.primerbocetoui.domain.repository

import com.alanturin.primerbocetoui.data.remote.model.Asignatura


interface ClasesAlumnoRepository {
    suspend fun getClases(studentId: Long): Result<List<Asignatura>>
}