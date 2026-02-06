package com.alanturin.primerbocetoui.domain.model

import java.time.LocalDate

data class CalendarEvent(
    val id: String,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val description: String? = null
)
