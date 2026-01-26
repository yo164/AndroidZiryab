package com.alanturin.primerbocetoui.ui.gestion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alanturin.primerbocetoui.R

@Composable
fun GestionAcademicaScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFA)),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        // 🎨 HEADER
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.title_gestion_academica),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF7C3AED)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = stringResource(id = R.string.subtitle_gestion_academica),
                    fontSize = 13.sp,
                    color = Color(0xFF9CA3AF),
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // 📱 TARJETA 1 - Ficha Usuario
        item {
            AcademicCardVertical(
                title = stringResource(id = R.string.menu_ficha_usuario),
                icon = Icons.Default.Person,
                backgroundColor = Color(0xFFF3E8FF),
                borderColor = Color(0xFFE9D5FF),
                iconColor = Color(0xFF7C3AED),
                contentDescription = stringResource(id = R.string.cd_ficha_usuario),
                onClick = { }
            )
        }

        // 📱 TARJETA 2 - Horario
        item {
            AcademicCardVertical(
                title = stringResource(id = R.string.menu_horario),
                icon = Icons.Default.Schedule,
                backgroundColor = Color(0xFFCCFBF1),
                borderColor = Color(0xFF99F6E0),
                iconColor = Color(0xFF14B8A6),
                contentDescription = stringResource(id = R.string.cd_horario),
                onClick = { }
            )
        }

        // 📱 TARJETA 3 - Calendario
        item {
            AcademicCardVertical(
                title = stringResource(id = R.string.menu_calendario),
                icon = Icons.Default.CalendarToday,
                backgroundColor = Color(0xFFFEF3C7),
                borderColor = Color(0xFFFCD34D),
                iconColor = Color(0xFFF59E0B),
                contentDescription = stringResource(id = R.string.cd_calendario),
                onClick = { }
            )
        }

        // 📱 TARJETA 4 - Tablón
        item {
            AcademicCardVertical(
                title = stringResource(id = R.string.menu_tablon),
                icon = Icons.Default.Notifications,
                backgroundColor = Color(0xFFFCE7F3),
                borderColor = Color(0xFFFBBF24),
                iconColor = Color(0xFFEC4899),
                contentDescription = stringResource(id = R.string.cd_tablon),
                onClick = { }
            )
        }
    }
}

@Composable
fun AcademicCardVertical(
    title: String,
    icon: ImageVector,
    backgroundColor: Color,
    borderColor: Color,
    iconColor: Color,
    contentDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                clip = false
            )
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 🎨 ICONO
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = contentDescription,
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
            }

            // 📝 TEXTO
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(1f))

            // ⬅️ BOTÓN FLECHA
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFF7C3AED),
                        shape = RoundedCornerShape(50.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "›",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
