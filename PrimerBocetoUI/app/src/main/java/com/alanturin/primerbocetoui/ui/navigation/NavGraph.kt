package com.alanturin.primerbocetoui.ui.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alanturin.primerbocetoui.ui.components.AppFooter
import com.alanturin.primerbocetoui.ui.components.AppHeader
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val startDestination = Route.Login
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBars = currentDestination?.hasRoute<Route.ClasesAlumno>() == true ||
            currentDestination?.hasRoute<Route.Gestion>() == true


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showBars) {
                AppHeader(
                    title = "Ziryab",
                    userName = "Alumno",
                    onLogout = {
                        FirebaseAuth.getInstance().signOut()

                        navController.navigate(Route.Login) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }
        },
        bottomBar = {
            if (showBars) {
                AppFooter(navController = navController)
            }
        }
    ) { innerPadding ->

        val contentModifier = Modifier
            .consumeWindowInsets(innerPadding)
            .padding(innerPadding)

        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            loginDestination(
                modifier = contentModifier,
                onLoginSuccess = {
                    navController.navigateToClasesAlumno()
                }
            )

            clasesAlumnoDestination(
                modifier = contentModifier,
                onAsignaturaClick = { id, nombre ->
                    navController.navigateToTemario(id, nombre)
                }
            )

            gestionDestination(
                modifier = contentModifier,
                onMenuClick = { id ->
                    when (id) {
                        1L -> navController.navigateToFichaUsuario()
                        2L -> navController.navigateToHorario()
                        3L -> navController.navigateToCalendario()
                        4L -> navController.navigateToTablon()
                    }
                }
            )

            temarioDestination(
                onBack = {
                    navController.popBackStack()
                }
            )

            fichaUsuarioDestination(modifier = contentModifier)

            horarioDestination(modifier = contentModifier)

            calendarioDestination(modifier = contentModifier)

            tablonDestination(modifier = contentModifier)
        }
    }
}