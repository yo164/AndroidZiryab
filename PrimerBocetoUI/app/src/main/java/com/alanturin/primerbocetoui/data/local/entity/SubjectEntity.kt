package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alanturin.primerbocetoui.domain.model.Asignatura

@Entity(tableName = "subject")
data class SubjectEntity(
    @PrimaryKey
    val id: Long,
    val idSubject: Int,
    val idGroup: Int,
    val nombre: String,
    val grade: String,
    val curso: String
)


fun SubjectEntity.toDomain() = Asignatura(
    id = this.id,
    idSubject = this.idSubject,
    idGroup = this.idGroup,
    nombre = this.nombre,
    grade = this.grade,
    curso = this.curso
)

fun Asignatura.toEntity() = SubjectEntity(
    id = this.id,
    idSubject = this.idSubject,
    idGroup = this.idGroup,
    nombre = this.nombre,
    grade = this.grade,
    curso = this.curso
)