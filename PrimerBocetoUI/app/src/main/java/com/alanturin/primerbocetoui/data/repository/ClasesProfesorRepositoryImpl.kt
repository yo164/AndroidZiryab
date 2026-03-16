package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.ClasesProfesorDataSource
import com.alanturin.primerbocetoui.data.local.datasource.SubjectLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.toDomain
import com.alanturin.primerbocetoui.data.local.entity.toEntity
import com.alanturin.primerbocetoui.domain.model.Asignatura
import com.alanturin.primerbocetoui.di.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ClasesProfesorRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: ClasesProfesorDataSource,
    private val localDataSource: SubjectLocalDataSource
) : ClasesProfesorRepository {
    override suspend fun readAll(profesorId: Long): Result<List<Asignatura>> {
        val local = localDataSource.getAll()
        if (local.isNotEmpty()) {
            android.util.Log.d("ZIRYAB", "Clases profesor desde LOCAL")
            return Result.success(local.map { it.toDomain() })
        }
        android.util.Log.d("ZIRYAB", "Clases profesor desde REMOTE")
        return remoteDataSource.readAll(profesorId).also { result ->
            result.onSuccess { lista ->
                localDataSource.insertAll(lista.map { it.toEntity() })
            }
        }    }

    override fun observe(profesorId: Long): Flow<Result<List<Asignatura>>> {
        return remoteDataSource.observe(profesorId)
    }
}