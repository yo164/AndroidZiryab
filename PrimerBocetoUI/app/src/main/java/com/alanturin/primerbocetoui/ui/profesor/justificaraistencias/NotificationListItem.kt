package com.alanturin.primerbocetoui.ui.profesor.justificaraistencias


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alanturin.primerbocetoui.domain.model.AssistanceItem

@Composable
fun NotificationListItem(
    asistencia: AssistanceItem,
    onClick: () -> Unit
) {
    val statusColor = when (asistencia.status) {
        "MISSING" -> Color.Red
        "LAG" -> Color(0xFFFFA500)
        "JUSTIFY" -> Color(0xFF4CAF50)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = asistencia.idStudent.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = asistencia.subjectName.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = asistencia.date.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = asistencia.startTime.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = asistencia.status,
                color = statusColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}