package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alanturin.primerbocetoui.domain.model.HorarioItem


@Entity(tableName = "horario")
data class HorarioEntity(
    @PrimaryKey
    val id: Int,
    val weekDay: Int,
    val startTime: String,
    val finishTime: String,
    val subjectName: String,
    val groupName: String,
    val idUser: Int,
    val role: String // "TEACHER" o "STUDENT"

)




fun HorarioEntity.toDomain() = HorarioItem(
    id = this.id.toLong(),
    weekDay = this.weekDay,
    startTime = this.startTime,
    finishTime = this.finishTime,
    subjectName = this.subjectName,
    groupName = this.groupName,
    idUser = this.idUser,
    role = this.role

)

fun HorarioItem.toEntity() = HorarioEntity(
    id = this.id.toInt(),
    weekDay = this.weekDay,
    startTime = this.startTime,
    finishTime = this.finishTime,
    subjectName = this.subjectName,
    groupName = this.groupName,
    idUser = this.idUser,
    role = this.role
)