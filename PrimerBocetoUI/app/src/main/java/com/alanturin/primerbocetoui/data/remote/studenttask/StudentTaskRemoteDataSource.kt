package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote

interface StudentTaskRemoteDataSource {
    suspend fun getByStudentEnrollment(enrollmentId: Int): Result<List<StudentTaskItemRemote>>
}