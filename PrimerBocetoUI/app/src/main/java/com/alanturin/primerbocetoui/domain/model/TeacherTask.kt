package com.alanturin.primerbocetoui.domain.model

data class TeacherTask(
    val id: Int,
    val idTeacherAssignment: Int,
    val title: String,
    val description: String?,
    val type: String,
    val startDate: String,
    val dueDate: String,
    val schoolYear: String,
    val createdAt: String
)
