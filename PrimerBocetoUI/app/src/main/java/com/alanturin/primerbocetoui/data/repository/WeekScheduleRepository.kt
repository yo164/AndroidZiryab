package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote
import com.alanturin.primerbocetoui.domain.model.HorarioItem

interface WeekScheduleRepository {
   // suspend fun getWeekScheduleByAssignment(idTeacherAssignment: Long): Result<List<WeekScheduleItemRemote>>

    suspend fun deleteAll()

    suspend fun getWeekScheduleByTeacher(idTeacher: Long, role: String): Result<List<HorarioItem>>
}