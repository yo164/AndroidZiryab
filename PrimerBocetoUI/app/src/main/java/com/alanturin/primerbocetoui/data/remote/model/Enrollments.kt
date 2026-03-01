package com.alanturin.primerbocetoui.data.remote.model


data class EnrollmentListRemote(
    val success: Boolean,
    val data: List<EnrollmentItemRemote>,
    val count: Int
)

data class EnrollmentItemRemote(
    val id: Int,
    val idStudent: Int,
    val idGroup: Int,
    val idSubject: Int,
    val schoolYear: String,
    val status: String,
    val student: Student
)

