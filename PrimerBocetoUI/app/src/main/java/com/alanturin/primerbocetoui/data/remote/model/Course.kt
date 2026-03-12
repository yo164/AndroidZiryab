package com.alanturin.primerbocetoui.data.remote.model

data class Course (
    val id: Int,
    val name: String,
    val duration: Int,
    val subjects: List<Subject>
)