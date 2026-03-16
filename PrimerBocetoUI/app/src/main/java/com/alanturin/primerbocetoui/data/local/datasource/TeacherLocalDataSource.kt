package com.alanturin.primerbocetoui.data.local.datasource

import com.alanturin.primerbocetoui.data.local.dao.TeacherDao
import com.alanturin.primerbocetoui.data.local.entity.TeacherEntity
import javax.inject.Inject

class TeacherLocalDataSource @Inject constructor(
    private val dao: TeacherDao
) {
    suspend fun getById(id: Int): TeacherEntity? = dao.getById(id)

    suspend fun insert(teacher: TeacherEntity) = dao.insert(teacher)

    suspend fun deleteAll() = dao.deleteAll()
}