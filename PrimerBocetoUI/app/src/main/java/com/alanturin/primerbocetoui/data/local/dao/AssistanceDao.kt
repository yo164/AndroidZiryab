package com.alanturin.primerbocetoui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alanturin.primerbocetoui.data.local.entity.AssistanceEntity

@Dao
interface AssistanceDao {

    @Query("SELECT * FROM assistance WHERE idSession = :idSession")
    suspend fun getBySessionId(idSession: Int): List<AssistanceEntity>

    @Query("SELECT * FROM assistance WHERE idStudent = :idStudent")
    suspend fun getByStudentId(idStudent: Int): List<AssistanceEntity>

    @Query("SELECT * FROM assistance WHERE (status = 'MISSING' OR status = 'LAG') AND uri IS NOT NULL AND idTeacher = :idTeacher")
    suspend fun getPendingJustifications(idTeacher: Int): List<AssistanceEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(assistances: List<AssistanceEntity>)

    @Query("UPDATE assistance SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Int, status: String)

    @Query("UPDATE assistance SET uri = :uri WHERE id = :id")
    suspend fun updateUri(id: Int, uri: String): Int

    @Query("SELECT * FROM assistance WHERE id = :id")
    suspend fun getById(id: Int): AssistanceEntity?

    @Query("DELETE FROM assistance")
    suspend fun deleteAll()


}