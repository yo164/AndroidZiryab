package com.alanturin.primerbocetoui.data.remote.model

data class CreateTaskRequestRemote(
    val idTeacherAssignment: Int,
    val title: String,
    val description: String?,
    val type: String,
    val startDate: String,
    val dueDate: String,
    val schoolYear: String
)

data class CreateTaskResponseRemote(
    val success: Boolean,
    val data: TaskItemRemote
)

data class TaskItemRemote(
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