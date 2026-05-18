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
    val attachmentUrl: String?,
    val task: TaskItemRemote? = null,
    val studentEnrollment: EnrollmentItemRemote? = null
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
    val attachmentUrl: String? = null
)

data class UploadFileDataRemote(
    val attachmentUrl: String
)

data class UploadFileResponseRemote(
    val success: Boolean,
    val message: String,
    val data: UploadFileDataRemote
)