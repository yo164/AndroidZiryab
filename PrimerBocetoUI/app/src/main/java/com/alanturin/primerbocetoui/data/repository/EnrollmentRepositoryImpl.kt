package com.alanturin.primerbocetoui.data.repository



import com.alanturin.primerbocetoui.data.remote.EnrollmentRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.EnrollmentItemRemote
import javax.inject.Inject

class EnrollmentRepositoryImpl @Inject constructor(
    private val remoteDataSource: EnrollmentRemoteDataSource
) : EnrollmentRepository {

    override suspend fun getEnrollmentsByFilters(
        idSubject: Int,
        idGroup: Int,
        schoolYear: String
    ): Result<List<EnrollmentItemRemote>> {
        return remoteDataSource.getEnrollmentsByFilters(idSubject, idGroup, schoolYear)
    }
}