package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote

interface StudentTaskRemoteDataSource {
    suspend fun getByStudentEnrollment(enrollmentId: Int): Result<List<StudentTaskItemRemote>>
    suspend fun submitTask(request: SubmitTaskRequestRemote): Result<StudentTaskItemRemote>
    suspend fun getSubmissionsByTask(taskId: Int): Result<List<StudentTaskItemRemote>>
}