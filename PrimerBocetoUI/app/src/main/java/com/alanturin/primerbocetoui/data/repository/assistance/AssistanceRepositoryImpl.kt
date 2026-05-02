package com.alanturin.primerbocetoui.data.repository.assistance

import com.alanturin.primerbocetoui.data.local.datasource.AssistanceLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.toDomain
import com.alanturin.primerbocetoui.data.local.entity.toEntity
import com.alanturin.primerbocetoui.data.remote.assistance.AssistanceRemoteDataSourceImpl
import com.alanturin.primerbocetoui.data.remote.assistance.forstudents.AssistanceForStudentsRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceBulkResponseRemote
import com.alanturin.primerbocetoui.data.remote.model.JustifyAssistanceRemoteResponse
import com.alanturin.primerbocetoui.data.remote.model.PatchAssistanceRemoteRequest
import com.alanturin.primerbocetoui.domain.model.AssistanceItem
import com.alanturin.primerbocetoui.domain.model.toAssistanceItem
import javax.inject.Inject

class AssistanceRepositoryImpl @Inject constructor(
    private val remoteDataSource: AssistanceRemoteDataSourceImpl,
    private val forStudentsRemote: AssistanceForStudentsRemoteDataSource,
    private val localDataSource: AssistanceLocalDataSource
) : AssistanceRepository {
    override suspend fun getAll(): Result<List<AssistanceItem>> {
        return remoteDataSource.getAll().map { lista ->
            android.util.Log.d("ZIRYAB", "Asistencias recibidas: ${lista.size}")
            lista.forEach { android.util.Log.d("ZIRYAB", "Asistencia: id=${it.id} idStudent=${it.studentEnrollment?.idStudent} idSession=${it.session?.id}") }

            val items = lista.map { it.toAssistanceItem() }
            localDataSource.insertAll(items.map { it.toEntity() })
            items
        }
    }

    override suspend fun deleteAll() {
        return localDataSource.deleteAll()
    }

    override suspend fun getPendingJustifications(idTeacher: Int): Result<List<AssistanceItem>> {
        val remoteResult = remoteDataSource.getPendingJustifications(idTeacher)

        return if (remoteResult.isSuccess) {
            remoteResult.mapCatching { lista -> lista.map { it.toAssistanceItem(0) } }
        } else {
            val local = localDataSource.getPendingJustifications(idTeacher)
            Result.success(local.map { it.toDomain() })
        }
    }

    override suspend fun getAssistancesBySessionId(id: Int): Result<List<AssistanceItem>> {
        val local = localDataSource.getBySessionId(id)
        if (local.isNotEmpty()) {
            android.util.Log.d("ZIRYAB", "Asistencias sesión desde LOCAL")
            return Result.success(local.map { it.toDomain() })
        }
        android.util.Log.d("ZIRYAB", "Asistencias sesión desde REMOTE")
        return remoteDataSource.getAssistanesbySessionId(id).map { lista ->
            val items = lista.map { it.toAssistanceItem()}
            items
        }


    }

    override suspend fun createBulk(request: AssistanceBulkRequestRemote): Result<AssistanceBulkResponseRemote> {
        return remoteDataSource.createBulk(request)
    }



    override suspend fun getByStudentId(idStudent: Int): Result<List<AssistanceItem>> {
        val local = localDataSource.getByStudentId(idStudent)
        if (local.isNotEmpty()) {
            android.util.Log.d("ZIRYAB", "Asistencias alumno desde LOCAL")
            return Result.success(local.map { it.toDomain() })
        }
        android.util.Log.d("ZIRYAB", "Asistencias alumno desde REMOTE")
        return forStudentsRemote.getByStudentId(idStudent).map { lista ->
            val items = lista.map { it.toAssistanceItem(idStudent) }
            localDataSource.insertAll(items.map { it.toEntity() })
            items
        }

    }

    override suspend fun justifyAssistancebyId(id: Int): Result<AssistanceItem> {
        val affectedRows = localDataSource.justifyAssistancebyId(id )
        remoteDataSource.justifyAssistanceStatusbyId(id)
        return if (affectedRows > 0) {
            val updated = localDataSource.getById(id)
            if (updated != null) Result.success(updated.toDomain())
            else Result.failure(RuntimeException("Error al recuperar asistencia"))
        } else {
            Result.failure(RuntimeException("Asistencia no encontrada"))
        }


    }

    override suspend fun patchAssistancebyId(id: Int, status: String): Result<JustifyAssistanceRemoteResponse> {

        return remoteDataSource.patchAssistanceStatusbyId(id,
            PatchAssistanceRemoteRequest(status)).also { result ->
            result.onSuccess { localDataSource.updateStatus(id, result.toString()) }
        }
    }



//justificationRequest
    override suspend fun justifyRequest(
        id: Int,
        uri: String
    ): Result<AssistanceItem> {
        val affectedRows = localDataSource.updateUri(id, uri)
        return if (affectedRows > 0) {
            val updated = localDataSource.getById(id)
            if (updated != null) Result.success(updated.toDomain())
            else Result.failure(RuntimeException("Error al recuperar asistencia"))
        } else {
            Result.failure(RuntimeException("Asistencia no encontrada"))
        }
    }
}