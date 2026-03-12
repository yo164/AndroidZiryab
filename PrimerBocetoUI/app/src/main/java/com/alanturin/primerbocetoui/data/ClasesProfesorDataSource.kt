package com.alanturin.primerbocetoui.data

import com.alanturin.primerbocetoui.domain.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface ClasesProfesorDataSource {
    suspend fun readAll(id:Long): Result<List<Asignatura>>
    fun observe(id:Long): Flow<Result<List<Asignatura>>>
}