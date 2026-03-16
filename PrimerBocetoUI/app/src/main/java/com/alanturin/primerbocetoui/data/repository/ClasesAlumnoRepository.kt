package com.alanturin.primerbocetoui.domain.repository

import com.alanturin.primerbocetoui.domain.model.Asignatura


interface ClasesAlumnoRepository {
    suspend fun getClases(studentId: Long): Result<List<Asignatura>>

    suspend fun getAll(): Result<List<Asignatura>>
    suspend fun getById(id: Long): Result<Asignatura?>
    suspend fun insertAll(subjects: List<Asignatura>)
    suspend fun deleteAll()
}