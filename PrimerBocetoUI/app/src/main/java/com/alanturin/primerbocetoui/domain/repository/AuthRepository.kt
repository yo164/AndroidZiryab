package com.alanturin.primerbocetoui.domain.repository

import com.alanturin.primerbocetoui.data.remote.model.LoginData

interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<Long>
}
