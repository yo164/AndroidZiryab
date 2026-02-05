package com.alanturin.primerbocetoui.ui.horario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HorarioScreen(modifier: Modifier = Modifier) {
    var selectedDayIndex by remember { mutableIntStateOf(0) }
    val days = listOf("LUN", "MAR", "MIÉ", "JUE", "VIE")

    val horario = mapOf(
        0 to listOf("IPE" to "08:15", "IPE" to "09:15", "Acceso a datos" to "10:15", "Acceso a datos" to "11:45", "Interfaces" to "12:45", "Interfaces" to "13:45"),
        1 to listOf("PSP" to "08:15", "Gestion" to "09:15", "Gestion" to "10:15", "PSP" to "11:45", "PSP" to "12:45", "PSP" to "13:45"),
        2 to listOf("Interfaces" to "08:15", "Interfaces" to "09:15", "Acceso a datos" to "10:15", "Acceso a datos" to "11:45", "Interfaces" to "12:45", "Interfaces" to "13:45"),
        3 to listOf("IPE" to "08:15", "IPE" to "09:15", "Acceso a datos" to "10:15", "Acceso a datos" to "11:45", "Interfaces" to "12:45", "Interfaces" to "13:45"),
        4 to listOf("PSP" to "08:15", "PSP" to "09:15", "Acceso a datos" to "10:15", "Acceso a datos" to "11:45", "Proyecto" to "12:45", "Proyecto" to "13:45"),
    )

    Column(modifier = modifier.fillMaxSize()) {

        // Pestañas de Días
        TabRow(
            selectedTabIndex = selectedDayIndex,
            containerColor = Color.White,
            contentColor = Color(0xFF7C3AED),
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedDayIndex]),
                    color = Color(0xFF7C3AED)
                )
            }
        ) {
            days.forEachIndexed { index, title ->
                Tab(
                    selected = selectedDayIndex == index,
                    onClick = { selectedDayIndex = index },
                    text = { Text(text = title, fontWeight = FontWeight.Bold) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Clases del día seleccionado
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val clasesDelDia = horario[selectedDayIndex] ?: emptyList()

            items(clasesDelDia) { (asignatura, hora) ->
                ClaseItem(asignatura = asignatura, hora = hora)
            }
        }
    }
}

@Composable
fun ClaseItem(asignatura: String, hora: String) {
    val isBreak = asignatura == "Recreo"
    val cardColor = if (isBreak) Color(0xFFF3F4F6) else Color.White
    val textColor = if (isBreak) Color.Gray else Color(0xFF1F2937)

    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isBreak) 0.dp else 2.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hora
            Box(
                modifier = Modifier
                    .background(Color(0xFFEDE9FE), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = hora,
                    color = Color(0xFF7C3AED),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = asignatura,
                color = textColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}