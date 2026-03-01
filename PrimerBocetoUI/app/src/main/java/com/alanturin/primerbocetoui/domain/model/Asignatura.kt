package com.alanturin.primerbocetoui.domain.model

data class Asignatura(
    val id: Long,
    val idSubject: Int,
    val idGroup: Int,
    val nombre: String,
    val grade: String,
    val curso: String
)