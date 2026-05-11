package com.alanturin.primerbocetoui.ui.gestion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.R

@Composable
fun GestionAcademicaScreen(
    modifier: Modifier = Modifier,
    viewModel: GestionAcademicaViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onMenuClick: (Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is GestionUiState.Initial -> {
            // Estado inicial, no hace nada
        }
        is GestionUiState.Loading -> {
            GestionLoadingScreen(modifier)
        }
        is GestionUiState.Success -> {
            GestionList(modifier, uiState, isDarkTheme, onDarkThemeChange, onMenuClick)
        }
    }
}

@Composable
private fun GestionLoadingScreen(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = Color(0xFF7C3AED)
        )
    }
}

@Composable
private fun GestionList(
    modifier: Modifier,
    uiState: GestionUiState,
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onMenuClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFA)),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        // HEADER
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

        // ITEMS
        item {
            ThemeToggleCard(
                isDarkTheme = isDarkTheme,
                onDarkThemeChange = onDarkThemeChange
            )
        }

        items(
            items = (uiState as GestionUiState.Success).options,
            key = { item -> item.id }
        ) { option ->
            GestionCardVertical(
                titleResId = option.titleResId,
                icon = option.icon,
                backgroundColor = option.backgroundColor,
                borderColor = option.borderColor,
                iconColor = option.iconColor,
                onClick = { onMenuClick(option.id) }
            )
        }
    }
}

@Composable
private fun ThemeToggleCard(
    isDarkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFEDE9FE),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                width = 2.dp,
                color = Color(0xFFDDD6FE),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(id = R.string.settings_theme_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF4C1D95)
            )
            Text(
                text = stringResource(id = R.string.settings_theme_subtitle),
                fontSize = 13.sp,
                color = Color(0xFF6B7280)
            )
        }

        Switch(
            checked = isDarkTheme,
            onCheckedChange = onDarkThemeChange
        )
    }
}

@Composable
fun GestionCardVertical(
    titleResId: Int,
    icon: ImageVector,
    backgroundColor: Color,
    borderColor: Color,
    iconColor: Color,
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
            // ICONO
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
                    contentDescription = stringResource(id = titleResId),
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
            }

            // TEXTO
            Text(
                text = stringResource(id = titleResId),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F2937),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.weight(1f))

            }
        }
    }

