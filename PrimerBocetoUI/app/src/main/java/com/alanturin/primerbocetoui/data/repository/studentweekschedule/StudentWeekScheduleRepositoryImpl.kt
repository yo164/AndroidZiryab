package com.alanturin.primerbocetoui.data.repository.studentweekschedule

import com.alanturin.primerbocetoui.data.remote.StudentWeekScheduleRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.StudentScheduleItemRemote
import com.alanturin.primerbocetoui.domain.model.HorarioItem
import com.alanturin.primerbocetoui.domain.model.toHorarioItem
import javax.inject.Inject

class StudentWeekScheduleRepositoryImpl @Inject constructor(
    private val studentWeekScheduleRemoteDataSource: StudentWeekScheduleRemoteDataSource
): StudentWeekScheduleRepository {
    override suspend fun getWeekScheduleByStudent(idStudent: Long): Result<List<HorarioItem>> {
        return studentWeekScheduleRemoteDataSource.getWeekScheduleByStudent(idStudent).map { lista ->
            lista.map { it.toHorarioItem() }
        }
    }
}