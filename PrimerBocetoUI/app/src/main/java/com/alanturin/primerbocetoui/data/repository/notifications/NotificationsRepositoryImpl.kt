package com.alanturin.primerbocetoui.data.repository.notifications

import com.alanturin.primerbocetoui.data.local.datasource.NotificationLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.toDomain
import com.alanturin.primerbocetoui.domain.model.NotificationInsertEntity
import com.alanturin.primerbocetoui.domain.model.NotificationItem
import com.alanturin.primerbocetoui.domain.model.toEntity
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
    private val localDataSource: NotificationLocalDataSource
) : NotificationsRepository {

    override suspend fun getAllNotifications(): Result<List<NotificationItem>> {
        return Result.success(localDataSource.getAll().map { it.toDomain() })
    }

    override suspend fun getNotificationById(id: Int): Result<NotificationItem?> {
        return Result.success(localDataSource.getById(id)?.toDomain())
    }



    override suspend fun insert(notification: NotificationInsertEntity) {
        localDataSource.insert(notification.toEntity())
    }

    override suspend fun updateStatus(id: Int, status: String, updatedAt: String) {
        localDataSource.updateStatus(id, status, updatedAt)
    }

    override suspend fun deleteAll() {
        localDataSource.deleteAll()
    }
}