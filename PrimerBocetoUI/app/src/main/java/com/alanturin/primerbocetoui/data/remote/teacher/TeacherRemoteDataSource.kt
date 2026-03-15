package com.alanturin.primerbocetoui.data.remote.teacher

import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote

interface TeacherRemoteDataSource {

    suspend fun getTeacherById(id: Int): Result<TeacherRemote>

}