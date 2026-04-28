package com.alanturin.primerbocetoui.data.remote.user

import com.alanturin.primerbocetoui.data.remote.model.ChangePasswordRequest
import com.alanturin.primerbocetoui.data.remote.model.UpdateProfileRequest
import com.alanturin.primerbocetoui.data.remote.model.UserProfileRemote
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val api: UserApi
) : UserRemoteDataSource {

    override suspend fun getProfile(): Result<UserProfileRemote> {
        return try {
            val response = api.getProfile()
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    Result.failure(RuntimeException("Body vacío"))
                } else {
                    Result.success(body)
                }
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfile(request: UpdateProfileRequest): Result<UserProfileRemote> {
        return try {
            val response = api.updateProfile(request)
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    Result.failure(RuntimeException("Body vacío"))
                } else {
                    Result.success(body)
                }
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(request: ChangePasswordRequest): Result<Unit> {
        return try {
            val response = api.changePassword(request)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(RuntimeException("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
