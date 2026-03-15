package com.alanturin.primerbocetoui.data.remote.assistance.forstudents

import com.alanturin.primerbocetoui.data.remote.model.AssistanceStudentItemRemote


interface AssistanceForStudentsRemoteDataSource {

    suspend fun getByStudentId(idStudent: Int): Result<List<AssistanceStudentItemRemote>>

}