package com.alanturin.primerbocetoui.model

data class ApiResponse(
    val data: List<AsignaturaItem>
)

data class AsignaturaItem(
    val subject: Subject,
    val course: Course?
)

data class Subject(
    val id: Int,
    val name: String
)

data class Course(
    val name: String
)