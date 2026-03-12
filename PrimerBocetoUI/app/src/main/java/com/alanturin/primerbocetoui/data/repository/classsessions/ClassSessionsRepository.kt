package com.alanturin.primerbocetoui.data.repository.classsessions

import com.alanturin.primerbocetoui.data.remote.model.SessionClassRemote

interface ClassSessionsRepository {


        suspend fun getActiveSession(idTeacherAssignment: Int): Result<SessionClassRemote>
}