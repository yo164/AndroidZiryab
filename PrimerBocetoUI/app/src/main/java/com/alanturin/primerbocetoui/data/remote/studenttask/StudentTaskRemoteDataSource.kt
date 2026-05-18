package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.UploadFileResponseRemote

interface StudentTaskRemoteDataSource {
    suspend fun getByStudentEnrollment(enrollmentId: Int): Result<List<StudentTaskItemRemote>>
    suspend fun submitTask(id: Int, request: SubmitTaskRequestRemote): Result<StudentTaskItemRemote>
    suspend fun getSubmissionsByTask(taskId: Int): Result<List<StudentTaskItemRemote>>
    suspend fun uploadFile(file: okhttp3.MultipartBody.Part): Result<UploadFileResponseRemote>
    suspend fun unsubmitTask(id: Int): Result<StudentTaskItemRemote>
}