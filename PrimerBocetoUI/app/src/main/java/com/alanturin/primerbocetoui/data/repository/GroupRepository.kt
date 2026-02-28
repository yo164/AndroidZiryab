package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.model.Group

interface GroupRepository {

    suspend fun getGroups(): List<Group>

}