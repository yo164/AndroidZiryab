package com.alanturin.primerbocetoui.data.remote.clasesalumno

import com.alanturin.primerbocetoui.domain.model.Asignatura

interface ClasesAlumnoRemoteDataSource {
    suspend fun getClasesAlumno(id: Long): Result<List<Asignatura>>

}