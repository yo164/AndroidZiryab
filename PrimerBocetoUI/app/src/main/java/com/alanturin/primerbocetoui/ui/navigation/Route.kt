package com.alanturin.primerbocetoui.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alanturin.primerbocetoui.ui.alumno.ClasesAlumnoScreen
import com.alanturin.primerbocetoui.ui.login.LoginScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
    @Serializable
    data object Login : Route()

    @Serializable
    data object ClasesAlumno : Route()
}

fun NavController.navigateToClasesAlumno() {
    this.navigate(Route.ClasesAlumno)
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
        ClasesAlumnoScreen()
    }
}
