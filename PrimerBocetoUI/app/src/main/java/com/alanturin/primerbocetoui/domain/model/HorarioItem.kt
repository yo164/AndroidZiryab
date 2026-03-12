package com.alanturin.primerbocetoui.domain.model

import com.alanturin.primerbocetoui.data.remote.model.StudentScheduleItemRemote
import com.alanturin.primerbocetoui.data.remote.model.WeekScheduleItemRemote

data class HorarioItem(
    val id: Long,
    val weekDay: Int,
    val startTime: String,
    val finishTime: String,
    val subjectName: String,
    val groupName: String
)


fun WeekScheduleItemRemote.toHorarioItem() = HorarioItem(
    id = this.id.toLong(),
    weekDay = this.weekDay,
    startTime = this.startTime,
    finishTime = this.finishTime,
    subjectName = this.teacherAssignment.subject.name,
    groupName = this.teacherAssignment.group.name
)

fun StudentScheduleItemRemote.toHorarioItem() = HorarioItem(
    id = this.id.toLong(),
    weekDay = this.weekDay,
    startTime = this.startTime,
    finishTime = this.finishTime,
    subjectName = this.teacherAssignment.subject.name,
    groupName = this.teacherAssignment.group.name
)