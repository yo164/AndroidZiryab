package com.alanturin.primerbocetoui.ui.profesor.listaAlumnos


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AlumnoCard(
    nombre: String,
    surname: String,
    ndSurname: String?,
    status: AssistanceStatus?,

    onAsistenciaChange: (AssistanceStatus) -> Unit

) {
    val apellidos = if (ndSurname != null) "$surname $ndSurname" else surname

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$nombre $apellidos",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Button(
                    onClick = {
                        onAsistenciaChange(AssistanceStatus.MISSING)
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (status == AssistanceStatus.MISSING) Color.Red else Color.LightGray
                    )
                ) {
                    Text("F", fontSize = 16.sp)
                }

                Button(
                    onClick = {
                        onAsistenciaChange(AssistanceStatus.LAG)
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (status == AssistanceStatus.LAG) Color(0xFFFFA500) else Color.LightGray
                    )
                ) {
                    Text("R", fontSize = 16.sp)
                }

                Button(
                    onClick = {
                        onAsistenciaChange(AssistanceStatus.PRESENT)
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (status == AssistanceStatus.PRESENT) Color(0xFF4CAF50) else Color.LightGray
                    )
                ) {
                    Text("A", fontSize = 16.sp)
                }
            }
        }
    }
}

