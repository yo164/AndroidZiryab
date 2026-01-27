package com.alanturin.primerbocetoui.ui.navigation

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    val startDestination = Route.Login

    Scaffold(
        modifier = Modifier.fillMaxSize()
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

            clasesAlumnoDestination(contentModifier)
        }
    }
}
