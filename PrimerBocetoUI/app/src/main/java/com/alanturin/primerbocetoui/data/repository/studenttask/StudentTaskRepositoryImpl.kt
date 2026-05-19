package com.alanturin.primerbocetoui.data.repository.studenttask

import com.alanturin.primerbocetoui.data.remote.model.GradeSubmissionRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.UploadFileResponseRemote
import com.alanturin.primerbocetoui.data.remote.studenttask.StudentTaskRemoteDataSource
import javax.inject.Inject

class StudentTaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudentTaskRemoteDataSource
) : StudentTaskRepository {
    override suspend fun getByStudentEnrollment(idStudentEnrollment: Int): Result<List<StudentTaskItemRemote>> {
        return remoteDataSource.getByStudentEnrollment(idStudentEnrollment)
    }

    override suspend fun submitTask(id: Int, request: SubmitTaskRequestRemote): Result<StudentTaskItemRemote> {
        return remoteDataSource.submitTask(id, request)
    }

    override suspend fun getSubmissionsByTask(taskId: Int): Result<List<StudentTaskItemRemote>> {
        return remoteDataSource.getSubmissionsByTask(taskId)
    }

    override suspend fun gradeSubmission(
        id: Int,
        request: GradeSubmissionRequestRemote
    ): Result<StudentTaskItemRemote> {
        return remoteDataSource.gradeSubmission(id, request)
    }

    override suspend fun uploadFile(file: okhttp3.MultipartBody.Part): Result<UploadFileResponseRemote> {
        return remoteDataSource.uploadFile(file)
    }

    override suspend fun unsubmitTask(id: Int): Result<StudentTaskItemRemote> {
        return remoteDataSource.unsubmitTask(id)
    }
}