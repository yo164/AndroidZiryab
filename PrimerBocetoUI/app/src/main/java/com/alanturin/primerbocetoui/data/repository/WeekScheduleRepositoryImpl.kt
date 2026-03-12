package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote
import com.alanturin.primerbocetoui.data.remote.WeekScheduleRemoteDataSource
import com.alanturin.primerbocetoui.domain.model.HorarioItem
import com.alanturin.primerbocetoui.domain.model.toHorarioItem
import javax.inject.Inject

class WeekScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeekScheduleRemoteDataSource
) : WeekScheduleRepository {

    /*override suspend fun getWeekScheduleByAssignment(idTeacherAssignment: Long): Result<List<WeekScheduleItemRemote>> {
        return remoteDataSource.getWeekScheduleByAssignment(idTeacherAssignment)
    }*/

    override suspend fun getWeekScheduleByTeacher(idTeacher: Long): Result<List<HorarioItem>> {
        return remoteDataSource.getWeekScheduleByTeacher(idTeacher).map { lista ->
            lista.map { it.toHorarioItem() }
        }
    }


}