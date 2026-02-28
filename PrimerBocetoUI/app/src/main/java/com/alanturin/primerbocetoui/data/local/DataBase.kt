package com.alanturin.primerbocetoui.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alanturin.primerbocetoui.data.local.dao.GroupDao
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity

@Database(entities = [GroupEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
}