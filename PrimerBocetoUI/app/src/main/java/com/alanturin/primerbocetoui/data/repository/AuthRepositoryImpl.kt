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

    /**
     * Autentica al usuario contra Firebase Auth y el backend Ziryab.
     *
     * @param email Correo electrónico del usuario.
     * @param pass Contraseña del usuario.
     * @return [Result] con el rol del usuario si el login es exitoso, o excepción si falla.
     */
    override suspend fun login(email: String, pass: String): Result<LoginData> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, pass).await()
            val user = authResult.user

            if (user == null) {
                throw Exception("Usuario nulo")
            }

            val firebaseUID = user.uid
            val request = LoginRequest(
                email = email,
                password = pass,
                firebaseUID = firebaseUID
            )

            val response = api.login(request)

            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) {
                    throw Exception("Body vacío")
                }
                val data = body.data
                Result.success(data)
            } else {
                auth.signOut()
                Result.failure(Exception("Error en servidor Ziryab: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
