package com.alanturin.primerbocetoui.data.remote

import javax.inject.Inject

class ClasesAlumnoRemoteDataSource @Inject constructor(
    private val api: ClasesAlumnoApi
) {
    suspend fun getClasesAlumno(id: Long) = api.getClasesAlumno(id)
}