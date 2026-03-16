package com.alanturin.primerbocetoui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alanturin.primerbocetoui.data.local.entity.SubjectEntity

@Dao
interface SubjectDao {

    @Query("SELECT * FROM subject")
    suspend fun getAll(): List<SubjectEntity>

    @Query("SELECT * FROM subject WHERE id = :id")
    suspend fun getById(id: Long): SubjectEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(subjects: List<SubjectEntity>)

    @Query("DELETE FROM subject")
    suspend fun deleteAll()
}