package com.alanturin.primerbocetoui.data.remote.clasesprofesor

import android.util.Log
import com.alanturin.primerbocetoui.data.ClasesProfesorDataSource
import com.alanturin.primerbocetoui.data.remote.model.toExternal
import com.alanturin.primerbocetoui.domain.model.Asignatura
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class ClasesProfesorRemoteDataSource @Inject constructor(
    private val api: ClasesProfesorApi,
    private val scope: CoroutineScope
) : ClasesProfesorDataSource {

    override fun observe(id: Long): Flow<Result<List<Asignatura>>> {
        return flow {
            emit(Result.success(emptyList()))
            val result = readAll(id)
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.Companion.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(id: Long): Result<List<Asignatura>> {
        return try {
            Log.d("DEBUG_APP", "Llamando a la API con ID: $id")

            val response = api.getAsignaturasProfesor(id) // <--- AQUÍ SE QUEDA PEGADO

            Log.d("DEBUG_APP", "Código respuesta: ${response.code()}")

            if (response.isSuccessful) {
                // ... tu código ...
                val remoteItems = response.body()?.data ?: emptyList()
                val domainItems = remoteItems.map { it.toExternal() }
                Result.success(domainItems)
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            // ¡¡¡AÑADE ESTA LÍNEA!!!
            Log.e("DEBUG_APP", "ERROR DE CONEXIÓN: ${e.message}")
            Result.failure(e)
        }
    }
}