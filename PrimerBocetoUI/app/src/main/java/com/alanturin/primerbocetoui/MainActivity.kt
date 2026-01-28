package com.alanturin.primerbocetoui

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alanturin.primerbocetoui.ui.screen.MainScreen
import com.alanturin.primerbocetoui.ui.theme.PrimerBocetoUITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimerBocetoUITheme {
                val userRole = "STUDENT"

                MainScreen(
                    userRole = userRole,
                    userName = "Estudiante Alumno",
                    userEmail = "Estudiante1@ziryab.es",
                    onLogout = {
                    }
                )
            }
        }
    }
}
