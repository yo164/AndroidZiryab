package com.alanturin.primerbocetoui.data.remote.model

data class AsignaturaListRemote(
    val success: Boolean,
    val data: List<AsignaturaItemRemote>,
    val count: Int
)

data class AsignaturaItemRemote(
    val id: Int,
    val schoolYear: String,
    val status: String,
    val subject: SubjectDataRemote,
    val group: GroupDataRemote
)

data class SubjectDataRemote(
    val id: Int,
    val name: String,
    val grade: String,
    val course: CourseDataRemote
)

data class CourseDataRemote(
    val id: Int,
    val name: String
)

data class GroupDataRemote(
    val id: Int,
    val name: String
)

fun AsignaturaItemRemote.toExternal(): Asignatura {
    return Asignatura(
        id = this.subject.id.toLong(),
        nombre = this.subject.name,
        curso = "${this.subject.course.name} - ${this.group.name}"
    )
}