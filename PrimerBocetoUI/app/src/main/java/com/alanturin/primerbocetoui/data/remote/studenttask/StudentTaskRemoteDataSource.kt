package com.alanturin.primerbocetoui.data.remote.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote

interface StudentTaskRemoteDataSource {

    suspend fun getByStudentEnrollment(idStudentEnrollment: Int): Result<List<StudentTaskItemRemote>>

}