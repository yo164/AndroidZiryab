package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.alanturin.primerbocetoui.domain.model.NotificationItem


data class NotificationWithAssistance(
    @Embedded val notification: NotificationEntity,
    @Relation(
        parentColumn = "idAssistance",
        entityColumn = "id"
    )
    val assistance: AssistanceEntity?
)

fun NotificationWithAssistance.toDomain() = NotificationItem(
    id = this.notification.id,
    idAssistance = this.notification.idAssistance,
    status = this.notification.status,
    createdAt = this.notification.createdAt,
    updatedAt = this.notification.updatedAt,
    subjectName = this.assistance?.subjectName,
    date = this.assistance?.date,
    startTime = this.assistance?.startTime,
    uri = this.assistance?.uri,
    idStudent = this.assistance?.idStudent,
    idSession = this.assistance?.idSession,
    idStudentEnrollment = this.assistance?.idStudentEnrollment,
    idTeacher = this.assistance?.idTeacher,
    assistanceStatus = this.assistance?.status
)