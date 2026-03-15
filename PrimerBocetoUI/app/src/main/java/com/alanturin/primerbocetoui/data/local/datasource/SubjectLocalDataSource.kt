package com.alanturin.primerbocetoui.data.local.datasource

import com.alanturin.primerbocetoui.data.local.dao.SubjectDao
import com.alanturin.primerbocetoui.data.local.entity.SubjectEntity
import javax.inject.Inject

class SubjectLocalDataSource @Inject constructor(
    private val dao: SubjectDao
) {
    suspend fun getAll(): List<SubjectEntity> = dao.getAll()

    suspend fun getById(id: Long): SubjectEntity? = dao.getById(id)

    suspend fun insertAll(subjects: List<SubjectEntity>) = dao.insertAll(subjects)

    suspend fun deleteAll() = dao.deleteAll()
}