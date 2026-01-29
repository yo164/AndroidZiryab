package com.alanturin.primerbocetoui.data.remote.model

import com.google.gson.annotations.SerializedName

// Estructura principal de la respuesta
data class AlumnoAsignaturaListRemote(
    val success: Boolean,
    val data: List<ItemRemote>,
    val count: Int
)

data class ItemRemote(
    val subject: SubjectRemote,
    val group: GroupRemote,
    val schoolYear: String
)

data class SubjectRemote(
    val id: Long,
    val name: String,
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
        id = this.subject.id,
        nombre = this.subject.name,
        curso = "${this.subject.course.name} - ${this.group.name}"
    )
}