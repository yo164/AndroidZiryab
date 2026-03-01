package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.ClasesAlumnoRemoteDataSource
import com.alanturin.primerbocetoui.domain.model.Asignatura
import com.alanturin.primerbocetoui.data.remote.model.toDomain
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import javax.inject.Inject

class ClasesAlumnoRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClasesAlumnoRemoteDataSource
) : ClasesAlumnoRepository {

    override suspend fun getClases(studentId: Long): Result<List<Asignatura>> {
        return try {
            val response = remoteDataSource.getClasesAlumno(studentId)

            if (response.isSuccessful) {
                val remoteList = response.body()?.data ?: emptyList()
                
                val domainList = remoteList.map { it.toDomain() }

                Result.success(domainList)
            } else {
                Result.failure(Exception("Error servidor: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}