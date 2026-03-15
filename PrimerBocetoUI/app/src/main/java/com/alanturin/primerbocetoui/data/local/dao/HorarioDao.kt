package com.alanturin.primerbocetoui.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity
import com.alanturin.primerbocetoui.data.local.entity.HorarioEntity

@Dao
interface HorarioDao {

    @Query("SELECT * FROM horario WHERE idUser = :idUser AND role = :role")
    suspend fun getByUserAndRole(idUser: Int, role: String): List<HorarioEntity>




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(horarios: List<HorarioEntity>)

    @Query("DELETE FROM horario")
    suspend fun deleteAll()
}