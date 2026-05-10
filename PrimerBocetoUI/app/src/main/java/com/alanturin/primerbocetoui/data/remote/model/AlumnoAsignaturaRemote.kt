package com.alanturin.primerbocetoui.data.remote.model

import com.alanturin.primerbocetoui.domain.model.Asignatura
import com.google.gson.annotations.SerializedName

// Estructura principal de la respuesta
data class AlumnoAsignaturaListRemote(
    val success: Boolean,
    val data: List<ItemRemote>,
    val count: Int
)

data class ItemRemote(
    val id: Long,
    val subject: SubjectRemote,
    val group: GroupRemote,
    val schoolYear: String,
)

data class SubjectRemote(
    val id: Long,
    val name: String,
    val grade: String,
    val idCourse: Long,
    val course: CourseRemote
)

data class CourseRemote(
    val id: Long,
    val name: String
)

data class GroupRemote(
    val id: Long,
    val name: String,
    val createdAt: String
)

fun ItemRemote.toDomain(): Asignatura {
    return Asignatura(
        id = this.id,
        idSubject = this.subject.id.toInt(),
        idGroup = this.group.id.toInt(),
        nombre = this.subject.name,
        grade = this.subject.grade,
        curso = "${this.subject.grade} de ${this.subject.course.name} - ${this.group.name}"
    )
}