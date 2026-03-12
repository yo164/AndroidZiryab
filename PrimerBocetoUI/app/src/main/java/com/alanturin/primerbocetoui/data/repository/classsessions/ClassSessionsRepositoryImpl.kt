package com.alanturin.primerbocetoui.data.repository.classsessions

import com.alanturin.primerbocetoui.data.remote.ClassSessionsRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.SessionClassRemote
import javax.inject.Inject

class ClassSessionsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClassSessionsRemoteDataSource
): ClassSessionsRepository{

    override suspend fun getActiveSession(idTeacherAssignment: Int): Result<SessionClassRemote> {
        return remoteDataSource.getActiveSession(idTeacherAssignment)
    }
}