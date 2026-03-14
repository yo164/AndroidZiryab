package com.alanturin.primerbocetoui.data.local.datasource

import com.alanturin.primerbocetoui.data.local.dao.HorarioDao
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity
import com.alanturin.primerbocetoui.data.local.entity.HorarioEntity
import com.alanturin.primerbocetoui.ui.navigation.Route
import javax.inject.Inject

class HorarioLocalDataSource @Inject constructor(
    private val dao: HorarioDao
) {

    suspend fun getAll(): List<HorarioEntity> = dao.getAll()

    suspend fun insertAll(horarios: List<HorarioEntity>) = dao.insertAll(horarios)

    suspend fun deleteAll() = dao.deleteAll()

}