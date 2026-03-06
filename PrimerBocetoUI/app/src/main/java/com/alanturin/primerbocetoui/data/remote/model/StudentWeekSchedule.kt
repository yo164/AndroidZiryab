package com.alanturin.primerbocetoui.data.remote.model



data class StudentScheduleListRemote(
    val success: Boolean,
    val data: List<StudentScheduleItemRemote>,
    val count: Int
)

data class StudentScheduleItemRemote(
    val id: Int,
    val idTeacherAssignment: Int,
    val weekDay: Int,
    val startTime: String,
    val finishTime: String,
    val teacherAssignment: StudentTeacherAssignmentRemote
)

data class StudentTeacherAssignmentRemote(
    val subject: StudentSubjectSimpleRemote,
    val group: StudentGroupSimpleRemote
)

data class StudentSubjectSimpleRemote(
    val name: String
)

data class StudentGroupSimpleRemote(
    val name: String
)