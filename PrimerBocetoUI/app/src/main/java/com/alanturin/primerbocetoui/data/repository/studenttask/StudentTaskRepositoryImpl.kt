package com.alanturin.primerbocetoui.data.repository.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.SubmitTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.studenttask.StudentTaskRemoteDataSource
import javax.inject.Inject

class StudentTaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudentTaskRemoteDataSource
) : StudentTaskRepository {
    override suspend fun getByStudentEnrollment(idStudentEnrollment: Int): Result<List<StudentTaskItemRemote>> {
        return remoteDataSource.getByStudentEnrollment(idStudentEnrollment)
    }

    override suspend fun submitTask(request: SubmitTaskRequestRemote): Result<StudentTaskItemRemote> {
        return remoteDataSource.submitTask(request)
    }

    override suspend fun getSubmissionsByTask(taskId: Int): Result<List<StudentTaskItemRemote>> {
        return remoteDataSource.getSubmissionsByTask(taskId)
    }
}