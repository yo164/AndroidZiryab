package com.alanturin.primerbocetoui.data.repository.studenttask

import com.alanturin.primerbocetoui.data.remote.model.StudentTaskItemRemote

interface StudentTaskRepository {
    suspend fun getByStudentEnrollment(idStudentEnrollment: Int): Result<List<StudentTaskItemRemote>>
}