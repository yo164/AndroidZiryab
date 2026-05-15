package com.alanturin.primerbocetoui.data.repository.studenttask

import com.alanturin.primerbocetoui.data.remote.model.GradeSubmissionRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote

interface StudentTaskRepository {
    suspend fun getByStudentEnrollment(idStudentEnrollment: Int): Result<List<StudentTaskItemRemote>>
    suspend fun submitTask(request: SubmitTaskRequestRemote): Result<StudentTaskItemRemote>
    suspend fun getSubmissionsByTask(taskId: Int): Result<List<StudentTaskItemRemote>>
    suspend fun gradeSubmission(id: Int, request: GradeSubmissionRequestRemote): Result<StudentTaskItemRemote>
}