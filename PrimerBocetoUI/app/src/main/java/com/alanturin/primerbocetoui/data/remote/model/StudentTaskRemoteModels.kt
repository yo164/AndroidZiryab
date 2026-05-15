package com.alanturin.primerbocetoui.data.remote.model

// En data/remote/model/
data class StudentTaskItemRemote(
    val id: Int,
    val title: String?,
    val description: String?,
    val type: String?,
    val idTask: Int,
    val idStudentEnrollment: Int,
    val status: String,
    val submissionDate: String?,
    val score: Double?,
    val feedback: String?,
    val attachmentUrl: String?,
    val task: TaskItemRemote
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

data class SubmitTaskRequestRemote(
    val idTask: Int,
    val idStudentEnrollment: Int,
    val attachmentUrl: String? = null
)

data class GradeSubmissionRequestRemote(
    val score: Double,
    val feedback: String? = null
)