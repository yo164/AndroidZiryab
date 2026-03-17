package com.alanturin.primerbocetoui.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.alanturin.primerbocetoui.R

sealed class BottomScreen(val titleResId: Int, val icon: ImageVector) {
    object Clases : BottomScreen(R.string.bottom_nav_clases, Icons.Default.School)
    object Gestion : BottomScreen(R.string.bottom_nav_gestion, Icons.Default.Settings)
}