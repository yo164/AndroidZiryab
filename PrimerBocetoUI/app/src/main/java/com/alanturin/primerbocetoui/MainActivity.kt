package com.alanturin.primerbocetoui

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.alanturin.primerbocetoui.ui.MainScreen
import com.alanturin.primerbocetoui.ui.profesor.ClasesProfesorScreen
import com.alanturin.primerbocetoui.ui.screen.ClasesAlumnoScreen
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
