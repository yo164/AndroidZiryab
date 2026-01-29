package com.alanturin.primerbocetoui.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alanturin.primerbocetoui.ui.alumno.ClasesAlumnoScreen
import com.alanturin.primerbocetoui.ui.gestion.GestionAcademicaScreen
import com.alanturin.primerbocetoui.ui.login.LoginScreen
import kotlinx.serialization.Serializable
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.ui.alumno.ClasesAlumnoViewModel

@Serializable
sealed class Route {
    @Serializable
    data object Login : Route()

    @Serializable
    data object ClasesAlumno : Route()

    // NUEVAS RUTAS DE GESTIÓN
    @Serializable
    data object Gestion : Route()

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
    this.navigate(Route.ClasesAlumno)
}

// NUEVAS FUNCIONES DE NAVEGACIÓN
fun NavController.navigateToGestion() {
    this.navigate(Route.Gestion)
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
    modifier: Modifier = Modifier
) {
    composable<Route.ClasesAlumno> {
        ClasesAlumnoScreen(
            viewModel = hiltViewModel(),
            onAsignaturaClick = { _, _ -> }
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

// NUEVOS DESTINATIONS (por ahora vacíos, para que agregues las pantallas)
fun NavGraphBuilder.fichaUsuarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.FichaUsuario> {
        // TODO: Tu FichaUsuarioScreen aquí
    }
}

fun NavGraphBuilder.horarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Horario> {
        // TODO: Tu HorarioScreen aquí
    }
}

fun NavGraphBuilder.calendarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Calendario> {
        // TODO: Tu CalendarioScreen aquí
    }
}

fun NavGraphBuilder.tablonDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Tablon> {
        // TODO: Tu TablonScreen aquí
    }
}
