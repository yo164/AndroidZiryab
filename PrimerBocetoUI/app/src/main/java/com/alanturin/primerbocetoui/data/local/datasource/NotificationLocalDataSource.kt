package com.alanturin.primerbocetoui.data.local.datasource

import com.alanturin.primerbocetoui.data.local.dao.NotificationDao
import com.alanturin.primerbocetoui.data.local.entity.NotificationEntity
import javax.inject.Inject

class NotificationLocalDataSource @Inject constructor(
    private val dao: NotificationDao
) {
    suspend fun getAll(): List<NotificationEntity> = dao.getAll()

    suspend fun getById(id: Int): NotificationEntity? = dao.getById(id)

    suspend fun insert(notification: NotificationEntity) = dao.insert(notification)

    suspend fun updateStatus(id: Int, status: String, updatedAt: String) = dao.updateStatus(id, status, updatedAt)

    suspend fun deleteAll() = dao.deleteAll()
}