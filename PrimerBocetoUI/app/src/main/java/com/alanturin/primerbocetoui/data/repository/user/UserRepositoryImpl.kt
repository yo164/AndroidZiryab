package com.alanturin.primerbocetoui.data.repository.user

import com.alanturin.primerbocetoui.data.remote.model.ChangePasswordRequest
import com.alanturin.primerbocetoui.data.remote.model.UpdateProfileRequest
import com.alanturin.primerbocetoui.data.remote.user.UserRemoteDataSource
import com.alanturin.primerbocetoui.domain.model.UpdatedProfile
import com.alanturin.primerbocetoui.domain.model.UserProfile
import com.alanturin.primerbocetoui.domain.model.toDomain
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getProfile(): Result<UserProfile> {
        return remoteDataSource.getProfile().map { it.toDomain() }
    }

    override suspend fun updateProfile(name: String, email: String): Result<UpdatedProfile> {
        val request = UpdateProfileRequest(
            name = name,
            email = email
        )
        return remoteDataSource.updateProfile(request).map { (remote, token) ->
            UpdatedProfile(remote.toDomain(), token)
        }
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): Result<Unit> {
        val request = ChangePasswordRequest(
            currentPassword = currentPassword,
            newPassword = newPassword,
            confirmPassword = confirmPassword
        )
        return remoteDataSource.changePassword(request)
    }
}
