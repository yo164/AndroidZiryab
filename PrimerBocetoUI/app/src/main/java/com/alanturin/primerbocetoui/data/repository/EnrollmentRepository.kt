package com.alanturin.primerbocetoui.data.repository



import com.alanturin.primerbocetoui.data.remote.model.EnrollmentItemRemote

interface EnrollmentRepository {
    suspend fun getEnrollmentsByFilters(
        idSubject: Int,
        idGroup: Int,
        schoolYear: String
    ): Result<List<EnrollmentItemRemote>>
}