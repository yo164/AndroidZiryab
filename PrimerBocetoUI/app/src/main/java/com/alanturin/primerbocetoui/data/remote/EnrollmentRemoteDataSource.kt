package com.alanturin.primerbocetoui.data.remote



import com.alanturin.primerbocetoui.data.remote.model.EnrollmentItemRemote
import javax.inject.Inject

class EnrollmentRemoteDataSource @Inject constructor(
    private val api: EnrollmentApi
) {
    suspend fun getEnrollmentsByFilters(
        idSubject: Int,
        idGroup: Int,
        schoolYear: String = "2024-2025"
    ): Result<List<EnrollmentItemRemote>> {
        return try {
            val response = api.getEnrollmentsByFilters(idSubject, idGroup, schoolYear)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    Result.failure(RuntimeException("Body vacío"))
                } else {
                    Result.success(body.data)
                }
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}