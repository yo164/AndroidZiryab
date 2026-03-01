package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote

interface WeekScheduleRepository {
    suspend fun getWeekScheduleByAssignment(idTeacherAssignment: Long): Result<List<WeekScheduleItemRemote>>
}