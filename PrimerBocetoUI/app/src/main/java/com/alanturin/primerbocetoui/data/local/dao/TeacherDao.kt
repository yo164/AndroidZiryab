package com.alanturin.primerbocetoui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alanturin.primerbocetoui.data.local.entity.TeacherEntity

@Dao
interface TeacherDao {


    @Query("SELECT * FROM teacher WHERE id = :id")
    suspend fun getById(id: Int): TeacherEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teacher: TeacherEntity)

    @Query("DELETE FROM teacher")
    suspend fun deleteAll()
}