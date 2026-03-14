package com.alanturin.primerbocetoui.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alanturin.primerbocetoui.data.local.dao.GroupDao
import com.alanturin.primerbocetoui.data.local.dao.HorarioDao
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity
import com.alanturin.primerbocetoui.data.local.entity.HorarioEntity

@Database(entities = [GroupEntity::class, HorarioEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao

    abstract fun horarioDao(): HorarioDao
}