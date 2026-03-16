package com.alanturin.primerbocetoui.data.repository.teacher

import com.alanturin.primerbocetoui.data.local.datasource.TeacherLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.toDomain
import com.alanturin.primerbocetoui.data.local.entity.toEntity
import com.alanturin.primerbocetoui.data.remote.model.TeacherRemote
import com.alanturin.primerbocetoui.data.remote.teacher.TeacherRemoteDataSource
import javax.inject.Inject

class TeacherRepositoryImpl @Inject constructor(
    private val remoteDataSource: TeacherRemoteDataSource,
    private val localDataSource: TeacherLocalDataSource
) : TeacherRepository {
    override suspend fun getTeacherById(id: Int): Result<TeacherRemote> {
        val local = localDataSource.getById(id)
        if (local != null) {
            android.util.Log.d("ZIRYAB", "Teacher desde LOCAL")
            return Result.success(local.toDomain())
        }
        android.util.Log.d("ZIRYAB", "Teacher desde REMOTE")
        return remoteDataSource.getTeacherById(id).also { result ->
            result.onSuccess { teacher ->
                localDataSource.insert(teacher.toEntity())
            }
        }
    }

}