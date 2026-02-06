package com.alanturin.primerbocetoui.domain.repository

import com.alanturin.primerbocetoui.domain.model.CalendarEvent
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    suspend fun getEvents(): Result<List<CalendarEvent>>
}
