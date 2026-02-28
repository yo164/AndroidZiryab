package com.alanturin.primerbocetoui.data.local.datasource

import com.alanturin.primerbocetoui.data.local.dao.GroupDao
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity
import javax.inject.Inject

class GroupLocalDataSource @Inject constructor(
    private val dao: GroupDao
) {
    suspend fun getAll(): List<GroupEntity> = dao.getAll()

    suspend fun insertAll(groups: List<GroupEntity>) = dao.insertAll(groups)

    suspend fun deleteAll() = dao.deleteAll()
}