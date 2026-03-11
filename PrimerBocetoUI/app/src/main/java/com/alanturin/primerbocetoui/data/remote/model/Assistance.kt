package com.alanturin.primerbocetoui.data.remote.model

data class AssistanceBulkRequestRemote(
    val assistances: List<AssistanceItemRequestRemote>
)
data class AssistancesBySessionResponseRemote(
    val success: Boolean,
    val data: List<AssistanceData>
)
data class AssistanceItemRequestRemote(
    val idSession: Int,
    val idStudentEnrollment: Int,
    val status: String
)

data class AssistanceBulkResponseRemote(
    val success: Boolean,
    val count: Int
)




data class JustifyAssistanceRemoteResponse(
    val success: Boolean,
    val data: AssistanceData
)

data class AssistanceData(
    val id: Int,
    val idSession: Int,
    val idStudentEnrollment: Int,
    val status: String,
    val createdAt: String
)

data class PatchAssistanceRemoteRequest(
    val status: String
)