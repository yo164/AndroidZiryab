package com.alanturin.primerbocetoui.data.remote.model

data class AssistanceBulkRequestRemote(
    val assistances: List<AssistanceItemRequestRemote>
)
data class AssistancesBySessionResponseRemote(
    val success: Boolean,
    val data: List<AssistanceItemRemote>
)
data class AssistanceItemRequestRemote(
    val idSession: Int,
    val idStudentEnrollment: Int,
    val status: String
)

data class AssistanceBulkResponseRemote(
    val success: Boolean,
    val count: Int
)




data class JustifyAssistanceRemoteResponse(
    val success: Boolean,
    val data: AssistanceItemRemote
)

data class AssistanceItemRemote(
    val id: Int,
    val idStudentEnrollment: Int,
    val status: String,
    val createdAt: String,
    val studentEnrollment: AssistanceStudentEnrollmentRemote?,
    val session: AssistanceSessionNestedRemote?
)

data class AssistanceSessionNestedRemote(
    val id: Int,
    val date: String,
    val schedule: ScheduleNestedRemote?
)

data class ScheduleNestedRemote(
    val startTime: String,
    val teacherAssignment: AssignmentNestedRemote?
)

data class AssignmentNestedRemote(
    val idTeacher: Int,
    val subject: SubjectNestedRemote?
)

data class SubjectNestedRemote(
    val name: String
)

data class AssistanceStudentEnrollmentRemote(
    val idStudent: Int
)

data class PatchAssistanceRemoteRequest(
    val status: String
)