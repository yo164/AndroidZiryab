package com.alanturin.primerbocetoui.data.remote.model

import com.alanturin.primerbocetoui.domain.model.TeacherTask

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

fun TaskItemRemote.toDomain(): TeacherTask = TeacherTask(
    id = id,
    idTeacherAssignment = idTeacherAssignment,
    title = title,
    description = description,
    type = type,
    startDate = startDate,
    dueDate = dueDate,
    schoolYear = schoolYear,
    createdAt = createdAt
)