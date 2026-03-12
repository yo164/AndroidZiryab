package com.alanturin.primerbocetoui.data.remote.model

data class WeekScheduleListRemote(
    val success: Boolean,
    val data: List<WeekScheduleItemRemote>,
    val count: Int
)

data class WeekScheduleItemRemote(
    val id: Int,
    val idTeacherAssignment: Int,
    val weekDay: Int,
    val startTime: String,
    val finishTime: String,
    val teacherAssignment: TeacherAssignmentRemote
)


data class TeacherAssignmentRemote(
    val subject: SubjectSimpleRemote,
    val group: GroupSimpleRemote
)

data class SubjectSimpleRemote(
    val name: String
)

data class GroupSimpleRemote(
    val name: String
)