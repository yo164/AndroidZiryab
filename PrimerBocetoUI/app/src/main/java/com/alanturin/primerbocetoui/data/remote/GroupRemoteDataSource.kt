package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.Group
import javax.inject.Inject

class GroupRemoteDataSource @Inject constructor(private val api: GroupApi) {

    suspend fun getGroups(): List<Group> {
        val response = api.getGroups()

        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                val lista = body.data
                return lista
            } else {
                return emptyList()
            }
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }
}

