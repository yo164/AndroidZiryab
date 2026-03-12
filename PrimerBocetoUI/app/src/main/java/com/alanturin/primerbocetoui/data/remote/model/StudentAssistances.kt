package com.alanturin.primerbocetoui.data.remote.model

data class AssistanceStudentResponseRemote(
    val success: Boolean,
    val data: List<AssistanceStudentItemRemote>,
    val count: Int
)

data class AssistanceStudentItemRemote(
    val id: Int,
    val status: String,
    val session: AssistanceSessionRemote
)

data class AssistanceSessionRemote(
    val date: String,
    val schedule: AssistanceScheduleRemote
)

data class AssistanceScheduleRemote(
    val startTime: String,
    val teacherAssignment: AssistanceTeacherAssignmentRemote
)

data class AssistanceTeacherAssignmentRemote(
    val subject: AssistanceSubjectRemote
)

data class AssistanceSubjectRemote(
    val name: String
)