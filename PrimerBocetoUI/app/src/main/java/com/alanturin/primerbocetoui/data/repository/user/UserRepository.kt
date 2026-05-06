package com.alanturin.primerbocetoui.data.repository.user

import com.alanturin.primerbocetoui.domain.model.UpdatedProfile
import com.alanturin.primerbocetoui.domain.model.UserProfile

interface UserRepository {

    suspend fun getProfile(): Result<UserProfile>

    suspend fun updateProfile(name: String, email: String): Result<UpdatedProfile>

    suspend fun changePassword(currentPassword: String, newPassword: String, confirmPassword: String): Result<Unit>
}
