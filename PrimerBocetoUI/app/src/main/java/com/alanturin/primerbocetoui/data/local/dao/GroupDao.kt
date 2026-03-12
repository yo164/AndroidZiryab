package com.alanturin.primerbocetoui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity

@Dao
interface GroupDao {

    @Query("SELECT * FROM grupo")
    suspend fun getAll(): List<GroupEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(groups: List<GroupEntity>)

    @Query("DELETE FROM grupo")
    suspend fun deleteAll()
}