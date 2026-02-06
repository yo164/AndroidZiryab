package com.alanturin.primerbocetoui.ui.calendario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.domain.model.CalendarEvent
import com.alanturin.primerbocetoui.domain.repository.CalendarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarioViewModel @Inject constructor(
    private val repository: CalendarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CalendarioUiState>(CalendarioUiState.Loading)
    val uiState: StateFlow<CalendarioUiState> = _uiState.asStateFlow()

    init {
        fetchEvents()
    }

    private fun fetchEvents() {
        viewModelScope.launch {
            _uiState.value = CalendarioUiState.Loading
            repository.getEvents()
                .onSuccess { events ->
                    val eventsByDate = mutableMapOf<LocalDate, MutableList<CalendarEvent>>()
                    
                    for (event in events) {
                        var currentDate = event.startDate
                        val effectiveEndDate = if (event.endDate.isAfter(event.startDate)) {
                            event.endDate
                        } else {
                            event.startDate.plusDays(1)
                        }

                        while (currentDate.isBefore(effectiveEndDate)) {
                            eventsByDate.getOrPut(currentDate) { mutableListOf() }.add(event)
                            currentDate = currentDate.plusDays(1)
                        }
                    }
                    _uiState.value = CalendarioUiState.Success(eventsByDate)
                }
                .onFailure {
                    _uiState.value = CalendarioUiState.Error("Error al cargar eventos")
                }
        }
    }
}

sealed class CalendarioUiState {
    object Loading : CalendarioUiState()
    data class Success(val events: Map<LocalDate, List<CalendarEvent>>) : CalendarioUiState()
    data class Error(val message: String) : CalendarioUiState()
}
