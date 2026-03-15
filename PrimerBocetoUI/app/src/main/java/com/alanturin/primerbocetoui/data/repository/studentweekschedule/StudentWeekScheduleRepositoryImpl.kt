package com.alanturin.primerbocetoui.data.repository.studentweekschedule

import com.alanturin.primerbocetoui.data.local.datasource.HorarioLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.toDomain
import com.alanturin.primerbocetoui.data.local.entity.toEntity
import com.alanturin.primerbocetoui.data.remote.StudentWeekScheduleRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.StudentScheduleItemRemote
import com.alanturin.primerbocetoui.domain.model.HorarioItem
import com.alanturin.primerbocetoui.domain.model.toHorarioItem
import com.alanturin.primerbocetoui.ui.session.SessionViewModel
import javax.inject.Inject

class StudentWeekScheduleRepositoryImpl @Inject constructor(
    private val studentWeekScheduleRemoteDataSource: StudentWeekScheduleRemoteDataSource,
    private val localDataSource: HorarioLocalDataSource
): StudentWeekScheduleRepository {
    override suspend fun deleteAll() {
        return localDataSource.deleteAll()
    }

    override suspend fun getWeekScheduleByStudent(idStudent: Long, role: String): Result<List<HorarioItem>> {
        val local = localDataSource.getByUserAndRole(idStudent.toInt(), role)
        if (local.isNotEmpty()) {
            return Result.success(local.map { it.toDomain() })
        }
        return studentWeekScheduleRemoteDataSource.getWeekScheduleByStudent(idStudent).map { lista ->
            val items = lista.map { it.toHorarioItem(role, idStudent.toInt()) }
            localDataSource.insertAll(items.map { it.toEntity() })
            items
        }
    }
}