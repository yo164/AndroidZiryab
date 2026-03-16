package com.alanturin.primerbocetoui.ui.theme

import com.alanturin.primerbocetoui.ui.theme.BackgroundDark
import com.alanturin.primerbocetoui.ui.theme.BackgroundLight
import com.alanturin.primerbocetoui.ui.theme.ErrorDark
import com.alanturin.primerbocetoui.ui.theme.ErrorLight
import com.alanturin.primerbocetoui.ui.theme.OnBackgroundDark
import com.alanturin.primerbocetoui.ui.theme.OnBackgroundLight
import com.alanturin.primerbocetoui.ui.theme.OnErrorDark
import com.alanturin.primerbocetoui.ui.theme.OnErrorLight
import com.alanturin.primerbocetoui.ui.theme.OnPrimaryDark
import com.alanturin.primerbocetoui.ui.theme.OnPrimaryLight
import com.alanturin.primerbocetoui.ui.theme.OnSecondaryDark
import com.alanturin.primerbocetoui.ui.theme.OnSecondaryLight
import com.alanturin.primerbocetoui.ui.theme.OnSurfaceDark
import com.alanturin.primerbocetoui.ui.theme.OnSurfaceLight
import com.alanturin.primerbocetoui.ui.theme.PrimaryDark
import com.alanturin.primerbocetoui.ui.theme.PrimaryLight
import com.alanturin.primerbocetoui.ui.theme.SecondaryDark
import com.alanturin.primerbocetoui.ui.theme.SecondaryLight
import com.alanturin.primerbocetoui.ui.theme.SurfaceDark
import com.alanturin.primerbocetoui.ui.theme.SurfaceLight
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = OnPrimaryDark,
    secondary = SecondaryDark,
    onSecondary = OnSecondaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = OnBackgroundDark,
    onSurface = OnSurfaceDark,
    error = ErrorDark,
    onError = OnErrorDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    secondary = SecondaryLight,
    onSecondary = OnSecondaryLight,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = OnBackgroundLight,
    onSurface = OnSurfaceLight,
    error = ErrorLight,
    onError = OnErrorLight

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun PrimerBocetoUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}