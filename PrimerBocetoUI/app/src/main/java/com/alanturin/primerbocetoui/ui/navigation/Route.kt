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
import com.alanturin.primerbocetoui.ui.alumno.TemarioAlumno.TemarioAlumnoScreen
import com.alanturin.primerbocetoui.ui.fichausuario.CambiarPasswordScreen
import com.alanturin.primerbocetoui.ui.fichausuario.EditarFichaScreen
import com.alanturin.primerbocetoui.ui.fichausuario.FichaUsuarioScreen
import com.alanturin.primerbocetoui.ui.fichausuario.justificar.JustificarFaltaScreen
import com.alanturin.primerbocetoui.ui.group.GroupScreen
import com.alanturin.primerbocetoui.ui.horario.HorarioScreen
import com.alanturin.primerbocetoui.ui.profesor.ClasesProfesorScreen
import com.alanturin.primerbocetoui.ui.profesor.gestionclases.GestionClasesScreen
import com.alanturin.primerbocetoui.ui.profesor.justificaraistencias.NotificationListScreen
import com.alanturin.primerbocetoui.ui.profesor.justificaraistencias.confirmajustificacion.JustificarScreen
import com.alanturin.primerbocetoui.ui.profesor.listaAlumnos.AlumnoListScreen
import com.alanturin.primerbocetoui.ui.profesor.tasks.TaskScreen

@Serializable
sealed class Route {
    @Serializable data object Login : Route()
    @Serializable data object ClasesAlumno : Route()
    @Serializable data object Gestion : Route()
    @Serializable data class Temario(val enrollmentId: Int, val nombre: String) : Route()

    @Serializable data object FichaUsuario : Route()
    @Serializable data object EditarFicha : Route()
    @Serializable data object CambiarPassword : Route()
    @Serializable data object Horario : Route()
    @Serializable data object Calendario : Route()
    @Serializable data object Tablon : Route()
    @Serializable data object Groups : Route()
    @Serializable data object ClasesProfesor : Route()
    @Serializable data object GestionClases : Route()
    @Serializable data object Task : Route()
    @Serializable data object AlumnoList: Route()

    @Serializable
    data object Notificaciones : Route()


    @Serializable
    data object JustificarScreen : Route()
    @Serializable
    data class JustificarFalta(
        val id: Int,
        val subjectName: String,
        val date: String,
        val startTime: String,
        val status: String
    ) : Route()
}

/**
 * Extensiones de navegación
 */
fun NavController.navigateToClasesAlumno() {
    this.navigate(Route.ClasesAlumno) { popUpTo(Route.Login) { inclusive = true } }
}

fun NavController.navigateToGestion() = this.navigate(Route.Gestion)

fun NavController.navigateToTemario(enrollmentId: Int, nombre: String) = this.navigate(Route.Temario(enrollmentId, nombre))

fun NavController.navigateToFichaUsuario() = this.navigate(Route.FichaUsuario)
fun NavController.navigateToEditarFicha() = this.navigate(Route.EditarFicha)
fun NavController.navigateToCambiarPassword() = this.navigate(Route.CambiarPassword)
fun NavController.navigateToHorario() = this.navigate(Route.Horario)
fun NavController.navigateToCalendario() = this.navigate(Route.Calendario)
fun NavController.navigateToTablon() = this.navigate(Route.Tablon)
fun NavController.navigateToGroups() = this.navigate(Route.Groups)
fun NavController.navigateToTask() = this.navigate(Route.Task)
fun NavController.navigateToAlumnoList() = this.navigate(Route.AlumnoList)

fun NavController.navigateToClasesProfesor() {
    this.navigate(Route.ClasesProfesor) { popUpTo(Route.Login) { inclusive = true } }
}

fun NavController.navigateToGestionClases() = this.navigate(Route.GestionClases)

fun NavController.navigateToNotificaciones() = this.navigate(Route.Notificaciones)


/**
 * Destinos del NavGraph
 */
fun NavGraphBuilder.loginDestination(
    modifier: Modifier = Modifier,
    onLoginSuccess: (role: String) -> Unit
) {
    composable<Route.Login> {
        LoginScreen(
            onLoginClick = { email, password, role ->
                if (email.isNotBlank() && password.isNotBlank()) onLoginSuccess(role)
            }
        )
    }
}

fun NavGraphBuilder.clasesAlumnoDestination(
    modifier: Modifier = Modifier,
    onAsignaturaClick: (Int, String) -> Unit
) {
    composable<Route.ClasesAlumno> {
        ClasesAlumnoScreen(viewModel = hiltViewModel(), onAsignaturaClick = onAsignaturaClick)
    }
}

fun NavGraphBuilder.gestionDestination(
    modifier: Modifier = Modifier,
    onMenuClick: (Long) -> Unit
) {
    composable<Route.Gestion> {
        GestionAcademicaScreen(modifier = modifier, onMenuClick = onMenuClick)
    }
}

fun NavGraphBuilder.fichaUsuarioDestination(
    modifier: Modifier = Modifier,
    navController: NavController,
    onEditarFicha: () -> Unit,
    onCambiarPassword: () -> Unit
) {
    composable<Route.FichaUsuario> {
        FichaUsuarioScreen(
            modifier = modifier,
            onJustificarClick = { id, subject, date, time, status ->
                navController.navigate(
                    Route.JustificarFalta(
                        id = id,
                        subjectName = subject,
                        date = date,
                        startTime = time,
                        status = status
                    )
                )
            },
            onEditarFicha = onEditarFicha,
            onCambiarPassword = onCambiarPassword
        )
    }
}

fun NavGraphBuilder.justificarFaltaDestination(
    modifier: Modifier = Modifier
) {
    composable<Route.JustificarFalta> { backStackEntry ->
        val data = backStackEntry.toRoute<Route.JustificarFalta>()
        JustificarFaltaScreen(
            id = data.id,
            subjectName = data.subjectName,
            date = data.date,
            startTime = data.startTime,
            status = data.status,
            modifier = modifier
        )
    }
}

fun NavGraphBuilder.temarioDestination(onBack: () -> Unit) {
    composable<Route.Temario> { backStackEntry ->
        val route: Route.Temario = backStackEntry.toRoute()
        TemarioAlumnoScreen(
            enrollmentId = route.enrollmentId,
            asignaturaNombre = route.nombre,
            onBack = onBack
        )
    }
}

fun NavGraphBuilder.horarioDestination(modifier: Modifier = Modifier) {
    composable<Route.Horario> { HorarioScreen() }
}

fun NavGraphBuilder.calendarioDestination(modifier: Modifier = Modifier) {
    composable<Route.Calendario> { com.alanturin.primerbocetoui.ui.calendario.CalendarioScreen(modifier = modifier) }
}

fun NavGraphBuilder.tablonDestination(modifier: Modifier = Modifier) {
    composable<Route.Tablon> { /* TODO */ }
}

fun NavGraphBuilder.groupsDestination(modifier: Modifier = Modifier) {
    composable<Route.Groups> { GroupScreen() }
}

fun NavGraphBuilder.clasesProfesorDestination(modifier: Modifier = Modifier, onGestionar: () -> Unit) {
    composable<Route.ClasesProfesor> { ClasesProfesorScreen(onGestionar = onGestionar) }
}

fun NavGraphBuilder.gestionClasesDestination(
    modifier: Modifier = Modifier,
    onNavigateToTasks: () -> Unit,
    onNavigateToListaAlumnos: () -> Unit
) {
    composable<Route.GestionClases> {
        GestionClasesScreen(onNavigateToTasks = onNavigateToTasks, onNavigateToListaAlumnos = onNavigateToListaAlumnos)
    }
}

fun NavGraphBuilder.taskDestination(modifier: Modifier = Modifier) {
    composable<Route.Task> { TaskScreen(viewModel = hiltViewModel()) }
}

fun NavGraphBuilder.alumnoListDestination(modifier: Modifier = Modifier) {
    composable<Route.AlumnoList> { AlumnoListScreen(modifier = modifier) }
}

fun NavGraphBuilder.notificacionesDestination(
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit
) {
    composable<Route.Notificaciones> {
        NotificationListScreen(onNotificationClick = onNotificationClick)
    }
}

fun NavGraphBuilder.justificarScreenDestination(modifier: Modifier = Modifier, onNavigateBack: () -> Unit) {
    composable<Route.JustificarScreen> {
        JustificarScreen(onNavigateBack = onNavigateBack)
    }
}

fun NavGraphBuilder.editarFichaDestination(onBack: () -> Unit) {
    composable<Route.EditarFicha> {
        EditarFichaScreen(onBack = onBack)
    }
}

fun NavGraphBuilder.cambiarPasswordDestination(onBack: () -> Unit) {
    composable<Route.CambiarPassword> {
        CambiarPasswordScreen(onBack = onBack)
    }
}

sealed class BottomNavItem(val route: Route, val icon: ImageVector, val label: String) {
    data object Clases : BottomNavItem(Route.ClasesAlumno, Icons.Default.Home, "Clases")
    data object Gestion : BottomNavItem(Route.Gestion, Icons.Default.List, "Gestión")
}