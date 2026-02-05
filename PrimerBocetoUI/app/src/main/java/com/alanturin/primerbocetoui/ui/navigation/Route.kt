package com.alanturin.primerbocetoui.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alanturin.primerbocetoui.ui.alumno.ClasesAlumnoScreen
import com.alanturin.primerbocetoui.ui.gestion.GestionAcademicaScreen
import com.alanturin.primerbocetoui.ui.login.LoginScreen
import kotlinx.serialization.Serializable
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.ui.UserProfile.FichaUsuarioScreen
import com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno.TemarioAlumnoScreen
import com.alanturin.primerbocetoui.ui.calendar.CalendarioScreen
import com.alanturin.primerbocetoui.ui.horario.HorarioScreen
import com.alanturin.primerbocetoui.ui.noticeBoard.TablonScreen

@Serializable
sealed class Route {
    @Serializable
    data object Login : Route()

    @Serializable
    data object ClasesAlumno : Route()

    @Serializable
    data object Gestion : Route()

    @Serializable
    data class Temario(val id: Long, val nombre: String) : Route()

    @Serializable
    data object FichaUsuario : Route()

    @Serializable
    data object Horario : Route()

    @Serializable
    data object Calendario : Route()

    @Serializable
    data object Tablon : Route()
}

fun NavController.navigateToClasesAlumno() {
    this.navigate(Route.ClasesAlumno) {
        popUpTo(Route.Login) { inclusive = true }
    }
}

fun NavController.navigateToGestion() {
    this.navigate(Route.Gestion)
}

fun NavController.navigateToTemario(id: Long, nombre: String) {
    this.navigate(Route.Temario(id, nombre))
}

fun NavController.navigateToFichaUsuario() {
    this.navigate(Route.FichaUsuario)
}

fun NavController.navigateToHorario() {
    this.navigate(Route.Horario)
}

fun NavController.navigateToCalendario() {
    this.navigate(Route.Calendario)
}

fun NavController.navigateToTablon() {
    this.navigate(Route.Tablon)
}

fun NavGraphBuilder.loginDestination(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit
) {
    composable<Route.Login> {
        LoginScreen(
            onLoginClick = { email: String, password: String ->
                if (email.isNotBlank() && password.isNotBlank()) {
                    onLoginSuccess()
                }
            }
        )
    }
}

fun NavGraphBuilder.clasesAlumnoDestination(
    modifier: Modifier = Modifier,
    onAsignaturaClick: (Long, String) -> Unit
) {
    composable<Route.ClasesAlumno> {
        ClasesAlumnoScreen(
            viewModel = hiltViewModel(),
            onAsignaturaClick = onAsignaturaClick
        )
    }
}

// NUEVO DESTINATION PARA GESTIÓN
fun NavGraphBuilder.gestionDestination(
    modifier: Modifier = Modifier,
    onMenuClick: (Long) -> Unit
) {
    composable<Route.Gestion> {
        GestionAcademicaScreen(
            modifier = modifier,
            onMenuClick = onMenuClick
        )
    }
}

fun NavGraphBuilder.temarioDestination(
    onBack: () -> Unit
) {
    composable<Route.Temario> { backStackEntry ->
        val route: Route.Temario = backStackEntry.toRoute()

        TemarioAlumnoScreen(
            asignaturaId = route.id,
            asignaturaNombre = route.nombre,
            userRole = "STUDENT",
            onBack = onBack
        )
    }
}

// 👇 AQUÍ CONECTAMOS LAS NUEVAS PANTALLAS
fun NavGraphBuilder.fichaUsuarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.FichaUsuario> {
        FichaUsuarioScreen(modifier = modifier)
    }
}

fun NavGraphBuilder.horarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Horario> {
        HorarioScreen(modifier = modifier)
    }
}

fun NavGraphBuilder.calendarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Calendario> {
        CalendarioScreen(modifier = modifier)
    }
}

fun NavGraphBuilder.tablonDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Tablon> {
        TablonScreen(modifier = modifier)
    }
}

sealed class BottomNavItem(val route: Route, val icon: ImageVector, val label: String) {
    data object Clases : BottomNavItem(Route.ClasesAlumno, Icons.Default.Home, "Clases")
    data object Gestion : BottomNavItem(Route.Gestion, Icons.Default.List, "Gestión")
}