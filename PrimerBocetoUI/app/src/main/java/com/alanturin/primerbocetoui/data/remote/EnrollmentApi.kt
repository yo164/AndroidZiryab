package com.alanturin.primerbocetoui.data.remote




import com.alanturin.primerbocetoui.data.remote.model.EnrollmentListRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EnrollmentApi {
    @GET("api/enrollments/by-filters")
    suspend fun getEnrollmentsByFilters(
        @Query("idSubject") idSubject: Int,
        @Query("idGroup") idGroup: Int,
        @Query("schoolYear") schoolYear: String
    ): Response<EnrollmentListRemote>
}