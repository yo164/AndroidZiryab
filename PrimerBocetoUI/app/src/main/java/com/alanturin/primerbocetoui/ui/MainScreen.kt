/*package com.alanturin.primerbocetoui.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno.TemarioAlumnoScreen
import com.alanturin.primerbocetoui.ui.components.UserMenu
import com.alanturin.primerbocetoui.ui.gestion.GestionAcademicaScreen
import com.alanturin.primerbocetoui.ui.navegation.BottomScreen
import com.alanturin.primerbocetoui.ui.profesor.ClasesProfesorScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    userRole: String,
    userName: String,
    userEmail: String,
    onLogout: () -> Unit
) {
    var currentScreen by remember { mutableStateOf<BottomScreen>(BottomScreen.Clases) }

    // 👇 ESTADO NUEVO: Guardamos la asignatura seleccionada (ID y Nombre)
    // Si es null = Estamos en la lista. Si tiene datos = Estamos en Temario.
    var selectedAsignatura by remember { mutableStateOf<Pair<Long, String>?>(null) }

    Scaffold(
        topBar = {
            // Solo mostramos la barra superior principal si NO hay asignatura seleccionada
            // (TemarioScreen tiene su propia barra con flecha atrás)
            if (selectedAsignatura == null) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Ziryab",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        UserMenu(userName = userName, userEmail = userEmail, onLogout = onLogout)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        },
        bottomBar = {
            // Ocultamos el menú inferior si estamos dentro de una asignatura
            if (selectedAsignatura == null) {
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
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 👇 LÓGICA DE NAVEGACIÓN
            if (selectedAsignatura != null) {
                // 1. SI HAY SELECCIÓN -> MOSTRAR TEMARIO
                TemarioAlumnoScreen(
                    asignaturaId = selectedAsignatura!!.first,
                    asignaturaNombre = selectedAsignatura!!.second,
                    userRole = userRole,
                    onBack = { selectedAsignatura = null } // Al volver, limpiamos selección
                )
            } else {
                // 2. SI NO -> NAVEGACIÓN NORMAL (Listas)
                when (currentScreen) {
                    is BottomScreen.Clases -> {
                        if (userRole == "TEACHER") {
                            ClasesProfesorScreen()
                        } else {
                            ClasesAlumnoScreen(
                                onAsignaturaClick = { id, nombre ->
                                    selectedAsignatura = id to nombre
                                }
                            )
                        }
                    }
                    is BottomScreen.Gestion -> {
                        GestionAcademicaScreen()
                    }
                }
            }
        }
    }
}*/