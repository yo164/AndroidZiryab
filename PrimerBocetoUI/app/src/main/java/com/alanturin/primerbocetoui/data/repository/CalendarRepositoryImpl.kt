package com.alanturin.primerbocetoui.data.repository

import com.alanturin.primerbocetoui.data.remote.CalendarApi
import com.alanturin.primerbocetoui.domain.model.CalendarEvent
import com.alanturin.primerbocetoui.domain.repository.CalendarRepository
import okhttp3.ResponseBody
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(
    private val api: CalendarApi
) : CalendarRepository {

    // URL pública del calendario
    private val calendarUrl = "https://calendar.google.com/calendar/ical/c_fe406644d4d1e624a708877c623f8c603b0c0122c6dcea64582acea2522e40ad%40group.calendar.google.com/public/basic.ics"

    override suspend fun getEvents(): Result<List<CalendarEvent>> {
        return try {
            val response: ResponseBody = api.downloadIcs(calendarUrl)
            val icsContent = response.string()
            val events = parseIcs(icsContent)
            Result.success(events)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseIcs(content: String): List<CalendarEvent> {
        val events = mutableListOf<CalendarEvent>()
        val lines = content.lines()
        
        var currentEvent: MutableMap<String, String>? = null
        
        for (line in lines) {
            val trimmedLine = line.trim()
            if (trimmedLine == "BEGIN:VEVENT") {
                currentEvent = mutableMapOf()
            } else if (trimmedLine == "END:VEVENT") {
                if (currentEvent != null) {
                    mapToEvent(currentEvent)?.let { events.add(it) }
                    currentEvent = null
                }
            } else if (currentEvent != null) {
                // Simple parsing: KEY:VALUE or KEY;PARAM:VALUE
                // Handle split strictly
                val parts = trimmedLine.split(":", limit = 2)
                if (parts.size == 2) {
                    val keyPart = parts[0]
                    val value = parts[1]
                    // If key has parameters (e.g. DTSTART;VALUE=DATE), just take the key name for simple matching or keep full
                    // For now, let's keep full key to distinguish VALUE=DATE
                    currentEvent[keyPart] = value
                }
            }
        }
        return events
    }

    private fun mapToEvent(data: Map<String, String>): CalendarEvent? {
        try {
            val summary = data["SUMMARY"] ?: "Sin título"
            val uid = data["UID"] ?: ""
            
            // Handle DTSTART
            // Can be DTSTART;VALUE=DATE:20260106
            // or DTSTART:20260106T120000Z
            
            var startDate: LocalDate? = null
            var endDate: LocalDate? = null

            // Find start key
            val startKey = data.keys.find { it.startsWith("DTSTART") }
            if (startKey != null) {
                startDate = parseDate(data[startKey]!!)
            }

            // Find end key
            val endKey = data.keys.find { it.startsWith("DTEND") }
            if (endKey != null) {
                endDate = parseDate(data[endKey]!!)
            }

            // If no end date, assume same as start
            if (startDate != null) {
                return CalendarEvent(
                    id = uid,
                    title = summary.replace("\\", ""), // Remove escapes
                    startDate = startDate,
                    endDate = endDate ?: startDate,
                    description = null
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun parseDate(dateString: String): LocalDate {
        // Formats: yyyyMMdd or yyyyMMddThhmmssZ
        return if (dateString.length == 8) {
            LocalDate.parse(dateString, DateTimeFormatter.BASIC_ISO_DATE)
        } else {
            // Take first 8 chars for date part if it's DateTime
            LocalDate.parse(dateString.substring(0, 8), DateTimeFormatter.BASIC_ISO_DATE)
        }
    }
}
