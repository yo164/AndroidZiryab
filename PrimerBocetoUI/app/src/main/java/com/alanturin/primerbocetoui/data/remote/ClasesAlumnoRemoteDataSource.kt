package com.alanturin.primerbocetoui.data.remote

import javax.inject.Inject


class ClasesAlumnoRemoteDataSource @Inject constructor(
    private val api: ClasesAlumnoApi
) {
    suspend fun getClasesAlumno(id: Long) = api.getClasesAlumno(id).also {
        android.util.Log.d("ZIRYAB", "Código: ${it.code()}")
        android.util.Log.d("ZIRYAB", "Body: ${it.body()}")
    }
}