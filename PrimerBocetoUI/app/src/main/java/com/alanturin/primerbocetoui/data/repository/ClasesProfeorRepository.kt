package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface ClasesProfesorRepository {
    suspend fun readAll(profesorId: Long): Result<List<Asignatura>>
    fun observe(profesorId: Long): Flow<Result<List<Asignatura>>>
}