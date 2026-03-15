package com.alanturin.primerbocetoui.data.repository.studentweekschedule

import com.alanturin.primerbocetoui.domain.model.HorarioItem

interface StudentWeekScheduleRepository {

    suspend fun deleteAll()

    suspend fun getWeekScheduleByStudent(idStudent: Long, role: String): Result<List<HorarioItem>>

}