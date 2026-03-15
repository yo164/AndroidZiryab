package com.alanturin.primerbocetoui.data.repository.teacher

import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote

interface TeacherRepository {

    suspend fun getTeacherById(id: Int): Result<TeacherRemote>

}