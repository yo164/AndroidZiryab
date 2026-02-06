package com.alanturin.primerbocetoui.ui.calendario

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale
import com.alanturin.primerbocetoui.domain.model.CalendarEvent
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun CalendarioScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarioViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = modifier.fillMaxSize().background(Color.White)) {
        when (val state = uiState) {
            is CalendarioUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF7C3AED))
                }
            }
            is CalendarioUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = Color.Red)
                }
            }
            is CalendarioUiState.Success -> {
                CalendarContent(events = state.events)
            }
        }
    }
}

@Composable
fun CalendarContent(events: Map<LocalDate, List<CalendarEvent>>) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(12) }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val daysOfWeek = remember { daysOfWeek() }
    
    // Estado para la fecha seleccionada
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    val calendarState = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first()
    )
    
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = remember { derivedStateOf { calendarState.firstVisibleMonth.yearMonth } }

    Column(modifier = Modifier.fillMaxSize()) {
        // Cabecera mes y navegación
        MonthHeader(
            yearMonth = visibleMonth.value,
            onPreviousClick = {
                coroutineScope.launch {
                    calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.minusMonths(1))
                }
            },
            onNextClick = {
                coroutineScope.launch {
                    calendarState.animateScrollToMonth(calendarState.firstVisibleMonth.yearMonth.plusMonths(1))
                }
            }
        )

        // Días de la semana
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es", "ES")).uppercase(),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Calendario
        HorizontalCalendar(
            state = calendarState,
            dayContent = { day ->
                val isSelected = selectedDate == day.date
                val isToday = day.date == LocalDate.now()
                val hasEvent = events[day.date]?.isNotEmpty() == true
                
                Day(
                    day = day,
                    isSelected = isSelected,
                    isToday = isToday,
                    hasEvent = hasEvent,
                    onClick = { clicked ->
                         selectedDate = clicked.date
                    }
                )
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Lista de eventos del día seleccionado
        Text(
            text = "Eventos del día",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937)
        )
        
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val dailyEvents = events[selectedDate] ?: emptyList()
            
            if (dailyEvents.isEmpty()) {
                item {
                    Text("No hay eventos", color = Color.Gray)
                }
            } else {
                items(dailyEvents) { event ->
                    EventItem(event)
                }
            }
        }
    }
}

@Composable
fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    isToday: Boolean,
    hasEvent: Boolean,
    onClick: (CalendarDay) -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) }
            ),
        contentAlignment = Alignment.Center
    ) {
        val backgroundColor = when {
            isSelected -> Color(0xFF7C3AED)
            // Eventos: Fondo morado muy muy suave para destacar
            day.position == DayPosition.MonthDate && hasEvent -> Color(0xFFF3E8FF) 
            else -> Color.Transparent
        }

        val textColor = when {
             isSelected -> Color.White
             day.position == DayPosition.MonthDate && hasEvent -> Color(0xFF5B21B6)
             day.position == DayPosition.MonthDate -> Color.Black
             else -> Color.Gray.copy(alpha = 0.5f)
        }

        if (day.position == DayPosition.MonthDate) {
            Box(
                modifier = Modifier
                    .size(36.dp) 
                    .clip(CircleShape)
                    .background(backgroundColor)
                    .then(
                        if (isToday && !isSelected && !hasEvent) Modifier.border(1.dp, Color(0xFF7C3AED), CircleShape) else Modifier
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = textColor,
                    fontWeight = if (isSelected || isToday || hasEvent) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun MonthHeader(
    yearMonth: YearMonth,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Anterior")
        }
        
        Text(
            text = yearMonth.month.getDisplayName(TextStyle.FULL, Locale("es", "ES")).capitalize() + " " + yearMonth.year,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F2937)
        )
        
        IconButton(onClick = onNextClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Siguiente")
        }
    }
}

@Composable
fun EventItem(event: CalendarEvent) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E8FF)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = event.title,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6D28D9)
            )
        }
    }
}

// Extension to capitalize first letter
fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale("es", "ES")) else it.toString() }
}
