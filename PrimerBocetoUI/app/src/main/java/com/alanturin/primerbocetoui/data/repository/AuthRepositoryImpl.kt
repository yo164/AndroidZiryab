package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.model.LoginData
import com.alanturin.primerbocetoui.data.remote.model.LoginRequest
import com.alanturin.primerbocetoui.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: com.alanturin.primerbocetoui.data.remote.AuthApi,
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, pass: String): Result<LoginData> {
        return try {
            val firebaseUser = auth.signInWithEmailAndPassword(email, pass).await().user
                ?: throw Exception("El usuario autenticado en Firebase es null.")
            val idToken = firebaseUser.getIdToken(false).await().token
                ?: throw Exception("No se obtuvo ID token de Firebase.")

            val response = api.login(LoginRequest(email, pass, idToken))

            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Respuesta vacía del servidor.")
                val jwt = body.token ?: throw Exception("El backend no devolvió token JWT.")
                val payload = body.data
                Result.success(LoginData(payload.id, payload.email, payload.name, payload.role, payload.firebaseUID, jwt))
            } else {
                auth.signOut()
                Result.failure(Exception("Error ${response.code()} en el login del backend."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
