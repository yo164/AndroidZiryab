package com.alanturin.primerbocetoui.data.remote.model

data class Teacher (
    val id: Int,
    val name: String,
    val lastName: String,
    val ndLastName: String,
    val dni: String,
    val fireBaseuid: String
)

data class TeacherResponseRemote(
    val success: Boolean,
    val data: TeacherRemote
)
data class TeacherRemote(
    val id: Int,
    val email: String,
    val name: String,
    val surname: String,
    val ndSurname: String?,
    val birthDate: String,
    val dni: String,
    val role: String
)
