package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alanturin.primerbocetoui.domain.model.AssistanceItem
@Entity(tableName = "assistance")
data class AssistanceEntity(
    @PrimaryKey
    val id: Int,
    val idStudent: Int,
    val idSession: Int?,
    val idStudentEnrollment: Int?,
    val idTeacher: Int?,

    val status: String,
    val subjectName: String?,
    val date: String?,
    val startTime: String?,
    val uri: String?
)

fun AssistanceEntity.toDomain() = AssistanceItem(
    id = this.id,
    idStudent = this.idStudent,
    idSession = this.idSession,
    idStudentEnrollment = this.idStudentEnrollment!!,
    idTeacher = this.idTeacher,
    status = this.status,
    subjectName = this.subjectName,
    date = this.date,
    startTime = this.startTime,
    uri = this.uri
)

fun AssistanceItem.toEntity() = AssistanceEntity(
    id = this.id,
    idStudent = this.idStudent,
    idSession = this.idSession,
    idStudentEnrollment = this.idStudentEnrollment,
    idTeacher = this.idTeacher,
    status = this.status,
    subjectName = this.subjectName,
    date = this.date,
    startTime = this.startTime,
    uri = this.uri
)