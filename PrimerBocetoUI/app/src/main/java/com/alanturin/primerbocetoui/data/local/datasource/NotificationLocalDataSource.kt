package com.alanturin.primerbocetoui.data.local.datasource

import com.alanturin.primerbocetoui.data.local.dao.NotificationDao
import com.alanturin.primerbocetoui.data.local.entity.NotificationEntity
import com.alanturin.primerbocetoui.data.local.entity.NotificationWithAssistance
import javax.inject.Inject

class NotificationLocalDataSource @Inject constructor(
    private val dao: NotificationDao
) {
    suspend fun getAll(): List<NotificationWithAssistance> = dao.getAll()

    suspend fun getById(id: Int): NotificationWithAssistance? = dao.getById(id)

    suspend fun insert(notification: NotificationEntity) = dao.insert(notification)

    suspend fun updateStatus(id: Int, status: String, updatedAt: String) = dao.updateStatus(id, status, updatedAt)

    suspend fun deleteAll() = dao.deleteAll()
}