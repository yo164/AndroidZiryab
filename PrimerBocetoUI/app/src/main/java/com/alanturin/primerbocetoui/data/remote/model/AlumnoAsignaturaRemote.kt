package com.alanturin.primerbocetoui.data.remote.model

import com.google.gson.annotations.SerializedName
// IMPORTANTE: Asegúrate de que este import apunte a donde tienes tu modelo Asignatura

data class AlumnoAsignaturaListRemote(
    @SerializedName("items")
    val items: List<AlumnoAsignaturaRemote>
)

data class AlumnoAsignaturaRemote(
    val id: Long,
    val name: String,
    val course: String,
    val group: String,
    val schoolYear: String?
)

fun AlumnoAsignaturaRemote.toDomain(): Asignatura {
    return Asignatura(
        id = this.id,
        nombre = this.name,
        curso = "${this.course} - ${this.group}"
    )
}