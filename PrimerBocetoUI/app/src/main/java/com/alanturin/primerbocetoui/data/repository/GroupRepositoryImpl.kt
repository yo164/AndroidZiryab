package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.local.datasource.GroupLocalDataSource
import com.alanturin.primerbocetoui.data.local.entity.GroupEntity
import com.alanturin.primerbocetoui.data.remote.GroupRemoteDataSource
import com.alanturin.primerbocetoui.data.remote.model.Group
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val remoteDataSource: GroupRemoteDataSource,
    private val localDataSource: GroupLocalDataSource
): GroupRepository {
    override suspend fun getGroups(): List<Group> {
        return try {
            val remoteGroups = remoteDataSource.getGroups()
            localDataSource.deleteAll()
            localDataSource.insertAll(remoteGroups.map {
                GroupEntity(
                    id = it.id,
                    name = it.name,
                    capacity = it.capacity,
                    createdAt = it.createdAt
                )
            })
            remoteGroups
        } catch (e: Exception) {
            localDataSource.getAll().map {
                Group(
                    id = it.id,
                    name = it.name,
                    capacity = it.capacity,
                    createdAt = it.createdAt
                )
            }
        }
    }

}
