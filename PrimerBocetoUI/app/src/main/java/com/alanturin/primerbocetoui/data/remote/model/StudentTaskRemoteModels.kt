package com.alanturin.primerbocetoui.data.remote.model

// En data/remote/model/
data class StudentTaskItemRemote(
    val id: Int,
    val idTask: Int,
    val idStudentEnrollment: Int,
    val status: String,
    val submissionDate: String?,
    val score: Double?,
    val feedback: String?,
    val attachmentUrl: String?
)

data class StudentTaskResponseRemote(
    val success: Boolean,
    val data: StudentTaskItemRemote
)

data class StudentTaskListResponseRemote(
    val success: Boolean,
    val data: List<StudentTaskItemRemote>,
    val count: Int
)