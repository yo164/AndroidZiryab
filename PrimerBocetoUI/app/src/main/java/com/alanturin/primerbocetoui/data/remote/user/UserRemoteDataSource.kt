package com.alanturin.primerbocetoui.data.remote.user

import com.alanturin.primerbocetoui.data.remote.model.ChangePasswordRequest
import com.alanturin.primerbocetoui.data.remote.model.UpdateProfileRequest
import com.alanturin.primerbocetoui.data.remote.model.UserProfileRemote

interface UserRemoteDataSource {

    suspend fun getProfile(): Result<UserProfileRemote>

    suspend fun updateProfile(request: UpdateProfileRequest): Result<Pair<UserProfileRemote, String?>>

    suspend fun changePassword(request: ChangePasswordRequest): Result<Unit>
}
