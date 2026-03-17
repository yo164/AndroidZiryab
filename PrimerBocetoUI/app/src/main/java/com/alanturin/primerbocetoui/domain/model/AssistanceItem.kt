package com.alanturin.primerbocetoui.domain.model

import com.alanturin.primerbocetoui.data.remote.model.AssistanceItemRemote
import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote

data class AssistanceItem(
    val id: Int,
    val idStudent: Int,
    val idSession: Int?,
    val idStudentEnrollment: Int,
    val idTeacher: Int?,
    val status: String,
    val subjectName: String?,
    val date: String?,
    val startTime: String?,
    val uri: String?
)
fun AssistanceStudentItemRemote.toAssistanceItem(idStudent: Int) = AssistanceItem(
    id = this.id,
    idStudent = idStudent,
    idSession = null,
    idStudentEnrollment = this.studentEnrollment.id,
    idTeacher = null,
    status = this.status,
    subjectName = this.session.schedule?.teacherAssignment?.subject?.name,
    date = this.session.date,
    startTime = this.session.schedule?.startTime,
    uri = null
)

fun AssistanceItemRemote.toAssistanceItem() = AssistanceItem(
    id = this.id,
    idStudent = this.studentEnrollment.idStudent,
    idSession = this.session.id,
    idStudentEnrollment = this.idStudentEnrollment,
    idTeacher = this.session.schedule?.teacherAssignment?.idTeacher,
    status = this.status,
    subjectName = this.session.schedule?.teacherAssignment?.subject?.name,
    date = this.session.date,
    startTime = this.session.schedule?.startTime,
    uri = null
)