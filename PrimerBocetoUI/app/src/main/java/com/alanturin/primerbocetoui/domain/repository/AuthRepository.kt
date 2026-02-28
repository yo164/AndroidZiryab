package com.alanturin.primerbocetoui.domain.repository

interface AuthRepository {
    suspend fun login(email: String, pass: String): Result<String>
}
