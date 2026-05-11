package com.alanturin.primerbocetoui.data.repository.task

import com.alanturin.primerbocetoui.data.remote.model.CreateTaskRequestRemote
import com.alanturin.primerbocetoui.data.remote.model.TaskItemRemote
import com.alanturin.primerbocetoui.data.remote.model.toDomain
import com.alanturin.primerbocetoui.data.remote.task.TaskRemoteDataSource
import com.alanturin.primerbocetoui.domain.model.TeacherTask
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val remoteDataSource: TaskRemoteDataSource
) : TaskRepository {

    override suspend fun createTask(request: CreateTaskRequestRemote): Result<TaskItemRemote> {
        return remoteDataSource.createTask(request)
    }

    override suspend fun getTaskById(id: Int): Result<TaskItemRemote> {
        return remoteDataSource.getTaskById(id)
    }

    override suspend fun getTasksByTeacherAssignment(idTeacherAssignment: Long): Result<List<TeacherTask>> {
        return remoteDataSource.getTasksByTeacherAssignment(idTeacherAssignment).map { list ->
            list.map { it.toDomain() }
        }
    }
}