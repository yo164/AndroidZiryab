package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: com.alanturin.primerbocetoui.data.remote.AuthApi,
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, pass: String): Result<Boolean> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, pass).await()
            val user = authResult.user ?: throw Exception("Error en Firebase Auth: Usuario nulo")
            val firebaseUID = user.uid

            val request = com.alanturin.primerbocetoui.data.remote.model.LoginRequest(
                email = email,
                password = pass,
                firebaseUID = firebaseUID
            )
            
            val response = api.login(request)

            if (response.isSuccessful) {
                Result.success(true)
            } else {
                auth.signOut()
                Result.failure(Exception("Error en servidor Ziryab: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
