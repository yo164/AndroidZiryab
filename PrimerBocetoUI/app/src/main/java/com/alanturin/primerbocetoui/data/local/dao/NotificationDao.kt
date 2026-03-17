package com.alanturin.primerbocetoui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.alanturin.primerbocetoui.data.local.entity.NotificationEntity
import com.alanturin.primerbocetoui.data.local.entity.NotificationWithAssistance
import com.alanturin.primerbocetoui.domain.model.NotificationInsertEntity

@Dao
interface NotificationDao {

    @Transaction
    @Query("SELECT * FROM notification")
    suspend fun getAll(): List<NotificationWithAssistance>

    @Query("SELECT * FROM notification WHERE id = :id")
    suspend fun getById(id: Int): NotificationWithAssistance?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: NotificationEntity)

    @Query("UPDATE notification SET status = :status, updatedAt = :updatedAt WHERE idAssistance = :idAssistance")
    suspend fun updateStatusByAssistanceId(idAssistance: Int, status: String, updatedAt: String)

    @Query("DELETE FROM notification")
    suspend fun deleteAll()
}