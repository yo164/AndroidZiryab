package com.alanturin.primerbocetoui.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alanturin.primerbocetoui.ui.navigation.Route
import com.alanturin.primerbocetoui.ui.navigation.navigateToClasesAlumno
import com.alanturin.primerbocetoui.ui.navigation.navigateToGestion
import com.alanturin.primerbocetoui.ui.shared.BottomScreen

val fondo = Color(0xFF7C3AED).copy(alpha = 0.15f)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppHeader(
    title: String,
    userName: String="alumno",
    userEmail: String="prueba@prueba.com",
    onLogout: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = "Ziryab",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            UserMenu(
                userName = userName,
                userEmail = userEmail,
                onLogout = onLogout
            )
            Spacer(modifier = Modifier.width(8.dp))
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = fondo,
            titleContentColor = Color(0xFF4C1D95),
            actionIconContentColor = Color(0xFF4C1D95)
        )
    )
}

@Composable
fun AppFooter(
    navController: NavController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomScreen.Clases,
        BottomScreen.Gestion
    )

    NavigationBar(
        modifier = Modifier.height(65.dp),
        containerColor = fondo
    ) {
        items.forEach { screen ->

            val isSelected = when (screen) {
                is BottomScreen.Clases -> currentDestination?.hasRoute<Route.ClasesAlumno>() == true
                is BottomScreen.Gestion -> currentDestination?.hasRoute<Route.Gestion>() == true
            }

            NavigationBarItem(
                modifier = Modifier.offset(y = 20.dp),

                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    )
                },
                selected = isSelected,
                onClick = {
                    when (screen) {
                        is BottomScreen.Clases -> navController.navigateToClasesAlumno()
                        is BottomScreen.Gestion -> navController.navigateToGestion()
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color(0xFF7C3AED),
                    selectedTextColor = Color(0xFF7C3AED),
                    unselectedIconColor = Color(0xFF6B7280),
                    unselectedTextColor = Color(0xFF6B7280)
                )
            )
        }
    }
}