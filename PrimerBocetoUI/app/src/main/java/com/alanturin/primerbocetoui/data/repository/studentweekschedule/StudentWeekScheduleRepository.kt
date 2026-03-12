package com.alanturin.primerbocetoui.data.repository.studentweekschedule

import com.alanturin.primerbocetoui.domain.model.HorarioItem

interface StudentWeekScheduleRepository {

    suspend fun getWeekScheduleByStudent(idStudent: Long): Result<List<HorarioItem>>

}