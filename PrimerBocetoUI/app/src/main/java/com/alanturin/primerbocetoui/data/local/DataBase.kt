package com.alanturin.primerbocetoui.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alanturin.primerbocetoui.data.local.dao.AssistanceDao
import com.alanturin.primerbocetoui.data.local.dao.GroupDao
import com.alanturin.primerbocetoui.data.local.dao.HorarioDao
import com.alanturin.primerbocetoui.data.local.dao.SubjectDao
import com.alanturin.primerbocetoui.data.local.dao.TeacherDao
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity
import com.alanturin.primerbocetoui.data.local.entity.HorarioEntity
import com.alanturin.primerbocetoui.data.local.entity.TeacherEntity
import com.alanturin.primerbocetoui.data.local.entity.AssistanceEntity
import com.alanturin.primerbocetoui.data.local.entity.SubjectEntity
import com.alanturin.primerbocetoui.model.Subject

@Database(
    entities = [
        GroupEntity::class,
        HorarioEntity::class,
        TeacherEntity::class,
        AssistanceEntity::class,
        SubjectEntity::class],
    version = 11,
    exportSchema = false)

abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao

    abstract fun horarioDao(): HorarioDao

    abstract fun teacherDao(): TeacherDao

    abstract fun assistanceDao(): AssistanceDao

    abstract fun subjectDao(): SubjectDao
}