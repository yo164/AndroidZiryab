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
import com.alanturin.primerbocetoui.domain.model.Asignatura
import com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno.TemarioAlumnoScreen
import com.alanturin.primerbocetoui.R
import com.alanturin.primerbocetoui.ui.fichausuario.FichaUsuarioScreen
import com.alanturin.primerbocetoui.ui.group.GroupScreen
import com.alanturin.primerbocetoui.ui.horario.HorarioScreen
import com.alanturin.primerbocetoui.ui.profesor.ClasesProfesorScreen
import com.alanturin.primerbocetoui.ui.profesor.gestionclases.GestionClasesScreen
import com.alanturin.primerbocetoui.ui.profesor.listaAlumnos.AlumnoListScreen
import com.alanturin.primerbocetoui.ui.profesor.tasks.TaskScreen

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

    @Serializable
    data object Groups : Route()

    /**
     * Ruta de la pantalla de asignaturas del profesor.
     */
    @Serializable
    data object ClasesProfesor : Route()

    /**
     * Ruta de la pantalla de gestión de clases del profesor.
     */
    @Serializable
    data object GestionClases : Route()

    /**
     * Ruta de la pantalla de Tasks
     * */
    @Serializable
    data object Task : Route()

    /**
     * Ruta dela pantalla de alumnos para pasar lista
     */
    @Serializable
    data object AlumnoList: Route()

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

/**
 * Navega a la pantalla de asignaturas del profesor
 * eliminando la pantalla de login de la pila de navegación.
 */
fun NavController.navigateToClasesProfesor() {
    this.navigate(Route.ClasesProfesor) {
        popUpTo(Route.Login) { inclusive = true }
    }
}

/**
 * Navega a la pantalla de gestión de clases.
 */
fun NavController.navigateToGestionClases() {
    this.navigate(Route.GestionClases)
}

/**
 * Navega a la pantalla de Tasks
 */
fun NavController.navigateToTask() {
    this.navigate(Route.Task)
}

/**
 * Navega a la pantalla de listaalumnos para pasar lista
 */

fun NavController.navigateToAlumnoList() {
    this.navigate(Route.AlumnoList)
}

fun NavGraphBuilder.loginDestination(
    modifier: Modifier = Modifier,
    onLoginSuccess: (role: String) -> Unit
) {
    composable<Route.Login> {
        LoginScreen(
            onLoginClick = { email: String, password: String, role: String ->
                if (email.isNotBlank() && password.isNotBlank()) {
                    onLoginSuccess(role)
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

/**
 * Define el destino de navegación para la pantalla de asignaturas del profesor.
 */
fun NavGraphBuilder.clasesProfesorDestination(
    modifier: Modifier = Modifier,
    onGestionar: () -> Unit
) {
    composable<Route.ClasesProfesor> {
        ClasesProfesorScreen(
            onGestionar = onGestionar
        )
    }
}

/**
 * Define el destino de navegación para la pantalla de gestión de clases.
 */
fun NavGraphBuilder.gestionClasesDestination(
    modifier: Modifier = Modifier,
    onNavigateToTasks: () -> Unit,
    onNavigateToListaAlumnos: () -> Unit
) {
    composable<Route.GestionClases> {
        GestionClasesScreen(
            onNavigateToTasks = onNavigateToTasks,
            onNavigateToListaAlumnos = onNavigateToListaAlumnos
        )
    }
}

/**
 * Define el destino de navegación para la pantalla de Tasks
 */
fun NavGraphBuilder.taskDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Task> {
        TaskScreen()
    }
}

/**
 * Define el destino de navegación para la pantalla de AlumnoList
 */
fun NavGraphBuilder.alumnoListDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.AlumnoList> {
        AlumnoListScreen( modifier = modifier)
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

fun NavGraphBuilder.fichaUsuarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.FichaUsuario> {
        FichaUsuarioScreen()
    }
}

fun NavGraphBuilder.horarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Horario> {
        HorarioScreen()
    }
}

fun NavGraphBuilder.calendarioDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Calendario> {
        com.alanturin.primerbocetoui.ui.calendario.CalendarioScreen(modifier = modifier)
    }
}

fun NavGraphBuilder.tablonDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Tablon> {
        // TODO: TablonScreen aquí
    }
}

fun NavController.navigateToGroups() {
    this.navigate(Route.Groups)
}

fun NavGraphBuilder.groupsDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.Groups> {
        GroupScreen()
    }
}

sealed class BottomNavItem(val route: Route, val icon: ImageVector, val label: String) {
    data object Clases : BottomNavItem(Route.ClasesAlumno, Icons.Default.Home, R.string.bottom_nav_clases)
    data object Gestion : BottomNavItem(Route.Gestion, Icons.Default.List, R.string.bottom_nav_gestion)
}