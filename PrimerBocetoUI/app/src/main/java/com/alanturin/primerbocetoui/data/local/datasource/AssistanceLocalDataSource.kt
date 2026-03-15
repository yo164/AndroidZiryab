package com.alanturin.primerbocetoui.data.local.datasource

import com.alanturin.primerbocetoui.data.local.dao.AssistanceDao
import com.alanturin.primerbocetoui.data.local.entity.AssistanceEntity
import com.alanturin.primerbocetoui.data.remote.model.Student
import javax.inject.Inject

class AssistanceLocalDataSource @Inject constructor(
    private val dao: AssistanceDao
) {
    suspend fun getBySessionId(idSession: Int): List<AssistanceEntity> = dao.getBySessionId(idSession)

    suspend fun getByStudentId(idStudent: Int): List<AssistanceEntity> = dao.getByStudentId(idStudent)

    suspend fun getPendingJustifications(): List<AssistanceEntity> = dao.getPendingJustifications()

    suspend fun insertAll(assistances: List<AssistanceEntity>) = dao.insertAll(assistances)

    suspend fun updateStatus(id: Int, status: String) = dao.updateStatus(id, status)

    suspend fun updateUri(id: Int, uri: String) = dao.updateUri(id, uri)

    suspend fun getById(id: Int): AssistanceEntity? = dao.getById(id)

    suspend fun deleteAll() = dao.deleteAll()
}