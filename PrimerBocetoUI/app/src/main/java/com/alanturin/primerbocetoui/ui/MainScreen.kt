package com.alanturin.primerbocetoui.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.alanturin.primerbocetoui.ui.components.UserMenu
import com.alanturin.primerbocetoui.ui.gestion.GestionAcademicaScreen
import com.alanturin.primerbocetoui.ui.navegation.BottomScreen
import com.alanturin.primerbocetoui.ui.profesor.ClasesProfesorScreen
import com.alanturin.primerbocetoui.ui.screen.ClasesAlumnoScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userRole: String,
    userName: String,
    userEmail: String,
    onLogout: () -> Unit
) {
    var currentScreen by remember { mutableStateOf<BottomScreen>(BottomScreen.Clases) }

    Scaffold(
        // barra superior
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Ziryab",
                        style = MaterialTheme.typography.headlineMedium, // Un poco más grande
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold // En negrita tipo logo
                    )
                },
                actions = {
                    UserMenu(
                        userName = userName,
                        userEmail = userEmail,
                        onLogout = onLogout
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        // menu inferior
        bottomBar = {
            NavigationBar {
                val items = listOf(BottomScreen.Clases, BottomScreen.Gestion)

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentScreen == screen,
                        onClick = { currentScreen = screen }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                is BottomScreen.Clases -> {
                    if (userRole == "TEACHER") {
                        // profesores
                        ClasesProfesorScreen()
                    } else {
                        // alumnos
                        ClasesAlumnoScreen()
                    }
                }
                is BottomScreen.Gestion -> {
                    // Pantalla de Gestión
                    GestionAcademicaScreen()
                }
            }
        }
    }
}