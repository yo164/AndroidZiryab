package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.local.datasource.HorarioLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.toDomain
import com.alanturin.primerbocetoui.data.local.entity.toEntity
import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote
import com.alanturin.primerbocetoui.data.remote.WeekScheduleRemoteDataSource
import com.alanturin.primerbocetoui.domain.model.HorarioItem
import com.alanturin.primerbocetoui.domain.model.toHorarioItem
import javax.inject.Inject

class WeekScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeekScheduleRemoteDataSource,
    private val localDataSource: HorarioLocalDataSource
) : WeekScheduleRepository {

    /*override suspend fun getWeekScheduleByAssignment(idTeacherAssignment: Long): Result<List<WeekScheduleItemRemote>> {
        return remoteDataSource.getWeekScheduleByAssignment(idTeacherAssignment)
    }*/

    override suspend fun getWeekScheduleByTeacher(idTeacher: Long): Result<List<HorarioItem>> {
        val local = localDataSource.getAll()
        if (local.isNotEmpty()) {
            return Result.success(local.map { it.toDomain() })
        }
        return remoteDataSource.getWeekScheduleByTeacher(idTeacher).map { lista ->
            val items = lista.map { it.toHorarioItem() }
            localDataSource.insertAll(items.map { it.toEntity() })
            items
        }
    }


}