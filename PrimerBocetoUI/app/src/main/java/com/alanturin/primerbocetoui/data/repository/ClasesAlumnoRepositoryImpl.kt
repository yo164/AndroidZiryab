package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.ClasesAlumnoRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.Asignatura
import com.alanturin.primerbocetoui.domain.repository.ClasesAlumnoRepository
import javax.inject.Inject

class ClasesAlumnoRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClasesAlumnoRemoteDataSource
) : ClasesAlumnoRepository {

    override suspend fun getClases(studentId: Long): Result<List<Asignatura>> {
        return try {
            // 1. Llamada al servidor
            val response = remoteDataSource.getClasesAlumno(studentId)

            if (response.isSuccessful) {
                val remoteList = response.body()?.items ?: emptyList()

                // 2. Mapeo MANUAL (para evitar errores con toDomain por ahora)
                val domainList = remoteList.map { item ->
                    Asignatura(
                        id = item.id,
                        nombre = item.name,
                        curso = "${item.course} - ${item.group}"
                    )
                }

                Result.success(domainList)
            } else {
                Result.failure(Exception("Error servidor: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}