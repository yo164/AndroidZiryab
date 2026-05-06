package com.alanturin.primerbocetoui.data.remote.user

import com.alanturin.primerbocetoui.data.remote.model.ChangePasswordRequest
import com.alanturin.primerbocetoui.data.remote.model.UpdateProfileRequest
import com.alanturin.primerbocetoui.data.remote.model.UpdateProfileResponse
import com.alanturin.primerbocetoui.data.remote.model.UserProfileRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UserApi {

    @GET("api/users/me")
    suspend fun getProfile(): Response<UserProfileRemote>

    @PATCH("api/users/me")
    suspend fun updateProfile(
        @Body request: UpdateProfileRequest
    ): Response<UpdateProfileResponse>

    @PATCH("api/users/me/password")
    suspend fun changePassword(
        @Body request: ChangePasswordRequest
    ): Response<Unit>
}
