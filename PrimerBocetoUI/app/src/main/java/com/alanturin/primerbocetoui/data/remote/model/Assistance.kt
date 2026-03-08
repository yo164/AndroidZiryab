package com.alanturin.primerbocetoui.data.remote.model

data class AssistanceBulkRequestRemote(
    val assistances: List<AssistanceItemRequestRemote>
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