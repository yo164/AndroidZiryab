package com.alanturin.primerbocetoui.data.repository.notifications

import com.alanturin.primerbocetoui.data.local.entity.NotificationWithAssistance
import com.alanturin.primerbocetoui.domain.model.NotificationInsertEntity
import com.alanturin.primerbocetoui.domain.model.NotificationItem

interface NotificationsRepository {

    suspend fun getAllNotifications(): Result<List<NotificationItem>>

    suspend fun getNotificationById(idTeacher: Int): Result<NotificationItem?>

    suspend fun insert(notification: NotificationInsertEntity)

    suspend fun updateStatus(id: Int, status: String, updatedAt: String)

    suspend fun deleteAll()

}