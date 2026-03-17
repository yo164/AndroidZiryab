package com.alanturin.primerbocetoui.data.repository.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote
import com.alanturin.primerbocetoui.data.remote.studenttask.StudentTaskRemoteDataSource
import javax.inject.Inject

class StudentTaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: StudentTaskRemoteDataSource
) : StudentTaskRepository {
    override suspend fun getByStudentEnrollment(idStudentEnrollment: Int): Result<List<StudentTaskItemRemote>> {
        return remoteDataSource.getByStudentEnrollment(idStudentEnrollment)
    }
}