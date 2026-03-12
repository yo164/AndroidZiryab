package com.alanturin.primerbocetoui.data.remote

import com.alanturin.primerbocetoui.data.remote.model.AlumnoAsignaturaListRemote
import com.alanturin.primerbocetoui.data.remote.model.Group
import com.alanturin.primerbocetoui.data.remote.model.GroupResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GroupApi {

    @GET("api/groups")
    suspend fun getGroups(): Response<GroupResponse>

}