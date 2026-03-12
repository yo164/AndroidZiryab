package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.ClasesProfesorDataSource
import com.alanturin.primerbocetoui.domain.model.Asignatura
import com.alanturin.primerbocetoui.di.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClasesProfesorRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ClasesProfesorDataSource
) : ClasesProfesorRepository {
    override suspend fun readAll(profesorId: Long): Result<List<Asignatura>> {
        return remoteDataSource.readAll(profesorId)
    }

    override fun observe(profesorId: Long): Flow<Result<List<Asignatura>>> {
        return remoteDataSource.observe(profesorId)
    }
}