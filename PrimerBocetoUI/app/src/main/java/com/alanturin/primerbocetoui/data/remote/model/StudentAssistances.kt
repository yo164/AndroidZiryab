package com.alanturin.primerbocetoui.data.remote.model

data class AssistanceStudentResponseRemote(
    val success: Boolean,
    val data: List<AssistanceStudentItemRemote>,
    val count: Int
)


data class AssistanceStudentItemRemote(
    val id: Int,
    val status: String,
    val session: AssistanceSessionRemote?,
    val studentEnrollment: StudentEnrollmentRemote?
)

data class StudentEnrollmentRemote(
    val id: Int,
    val student: StudentRemote
)

data class StudentRemote(
    val id: Int,
    val name: String
)

data class AssistanceSessionRemote(
    val id: Int,
    val date: String,
    val schedule: AssistanceScheduleRemote?
)

data class AssistanceScheduleRemote(
    val startTime: String,
    val teacherAssignment: AssistanceTeacherAssignmentRemote?
)

data class AssistanceTeacherAssignmentRemote(
    val subject: AssistanceSubjectRemote
)

data class AssistanceSubjectRemote(
    val name: String
)