package com.alanturin.primerbocetoui.data.remote.model

data class Subject (
    val  id: Int,
    val name: String,
    val grade: Int,
    val course: Course
)