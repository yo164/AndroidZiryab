package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.model.LoginData
import com.alanturin.primerbocetoui.data.remote.model.LoginRequest
import com.alanturin.primerbocetoui.domain.repository.AuthRepository
import com.alanturin.primerbocetoui.data.remote.model.LoginRequest
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: com.alanturin.primerbocetoui.data.remote.AuthApi,
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, pass: String): Result<Long> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, pass).await()
            val user = authResult.user ?: throw Exception("El usuario autenticado en Firebase es null.")
            val firebaseUID = user.uid

            val request = LoginRequest(
                email = email,
                password = pass,
                firebaseUID = firebaseUID
            )

            val response = api.login(request)

            if (response.isSuccessful) {
                val userId = response.body()?.data?.id ?: throw Exception("El ID del usuario es nulo en la respuesta")
                Result.success(userId)
            } else {
                auth.signOut()
                Result.failure(Exception("Error en Login del backend: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
