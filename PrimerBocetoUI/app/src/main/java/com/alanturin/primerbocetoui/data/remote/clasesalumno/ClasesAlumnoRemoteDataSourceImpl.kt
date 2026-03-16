package com.alanturin.primerbocetoui.data.remote.clasesalumno

import android.util.Log
import com.alanturin.primerbocetoui.data.remote.model.toDomain
import javax.inject.Inject
import com.alanturin.primerbocetoui.domain.model.Asignatura

class ClasesAlumnoRemoteDataSourceImpl @Inject constructor(
    private val api: ClasesAlumnoApi
): ClasesAlumnoRemoteDataSource {
    override suspend fun getClasesAlumno(id: Long): Result<List<Asignatura>> {
        return try {
            val response = api.getClasesAlumno(id)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    Result.failure(RuntimeException("Body vacío"))
                } else {
                    Result.success(body.data.map { it.toDomain() })
                }
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}