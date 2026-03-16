package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.local.datasource.SubjectLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.toDomain
import com.alanturin.primerbocetoui.data.local.entity.toEntity
import com.alanturin.primerbocetoui.data.remote.clasesalumno.ClasesAlumnoRemoteDataSource
import com.alanturin.primerbocetoui.domain.model.Asignatura
import com.alanturin.primerbocetoui.data.remote.model.toDomain
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import javax.inject.Inject

class ClasesAlumnoRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClasesAlumnoRemoteDataSource,
    private val localDataSource: SubjectLocalDataSource
) : ClasesAlumnoRepository {

    override suspend fun getClases(studentId: Long): Result<List<Asignatura>> {
        val local = localDataSource.getAll()
        if (local.isNotEmpty()) {
            android.util.Log.d("ZIRYAB", "Clases desde LOCAL")
            return Result.success(local.map { it.toDomain() })
        }
        android.util.Log.d("ZIRYAB", "Clases desde REMOTE")
        return remoteDataSource.getClasesAlumno(studentId).also { result ->
            result.onSuccess { lista ->
                lista.forEach {
                    android.util.Log.d("ZIRYAB", "Asignatura: nombre=${it.nombre} grade=${it.grade} curso=${it.curso}")
                }
                localDataSource.insertAll(lista.map { it.toEntity() })
            }
        }
    }
    override suspend fun getAll(): Result<List<Asignatura>> {
        return Result.success(localDataSource.getAll().map { it.toDomain() })
    }

    override suspend fun getById(id: Long): Result<Asignatura?> {
        return Result.success(localDataSource.getById(id)?.toDomain())
    }

    override suspend fun insertAll(subjects: List<Asignatura>) {
        localDataSource.insertAll(subjects.map { it.toEntity() })
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }
}