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
import com.alanturin.primerbocetoui.domain.model.NotificationItem

@Composable
fun NotificationListItem(
    datosAsistencia: NotificationItem,
    onClick: () -> Unit
) {
    val statusColor = when (datosAsistencia.status) {
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
                    text = datosAsistencia.idStudent.toString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = datosAsistencia.subjectName.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = datosAsistencia.date.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = datosAsistencia.startTime.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = datosAsistencia.status,
                color = statusColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}