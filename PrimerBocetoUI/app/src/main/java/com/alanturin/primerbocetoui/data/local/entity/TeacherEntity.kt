package com.alanturin.primerbocetoui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote

@Entity(tableName = "teacher")
data class TeacherEntity(
    @PrimaryKey
    val id: Int,
    val email: String,
    val name: String,
    val surname: String,
    val ndSurname: String?,
    val birthDate: String,
    val dni: String,
    val role: String
)

fun TeacherEntity.toDomain() = TeacherRemote(
    id = this.id,
    email = this.email,
    name = this.name,
    surname = this.surname,
    ndSurname = this.ndSurname,
    birthDate = this.birthDate,
    dni = this.dni,
    role = this.role
)

fun TeacherRemote.toEntity() = TeacherEntity(
    id = this.id,
    email = this.email,
    name = this.name,
    surname = this.surname,
    ndSurname = this.ndSurname,
    birthDate = this.birthDate,
    dni = this.dni,
    role = this.role
)