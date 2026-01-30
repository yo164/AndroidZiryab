package com.alanturin.primerbocetoui.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomScreen(val title: String, val icon: ImageVector) {
    object Clases : BottomScreen("Asignaturas", Icons.Default.School)
    object Gestion : BottomScreen("Gestión", Icons.Default.Settings)
}