package com.alanturin.primerbocetoui.data.remote.model

import com.google.gson.annotations.SerializedName

data class AsignaturaListRemote(
    @SerializedName("items")
    val items: List<AsignaturaItemRemote>
)

data class AsignaturaItemRemote(
    @SerializedName("subject")
    val subject: SubjectDataRemote
)

data class SubjectDataRemote(
    val id: Long,
    val name: String,
    @SerializedName("course")
    val course: CourseDataRemote?
)

data class CourseDataRemote(
    val id: Long?,
    val name: String?
)

fun AsignaturaItemRemote.toExternal(): Asignatura {
    return Asignatura(
        id = this.subject.id,
        nombre = this.subject.name, // Aquí ya no será null
        curso = this.subject.course?.name ?: "Sin curso asignado"
    )
}