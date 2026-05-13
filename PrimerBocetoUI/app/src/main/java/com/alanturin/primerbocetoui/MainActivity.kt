package com.alanturin.primerbocetoui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.alanturin.primerbocetoui.ui.navigation.NavGraph
import com.alanturin.primerbocetoui.ui.theme.PrimerBocetoUITheme
import com.alanturin.primerbocetoui.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            val isDarkTheme by themeViewModel.isDarkTheme
                .collectAsState(initial = false)

            PrimerBocetoUITheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {

                        // Llamada a tu LoginScreen
                        /*LoginScreen {_, _ ->

                        }*/
                        //Llamada a Dashboad del alumno
                        NavGraph(
                            isDarkTheme = isDarkTheme,
                            onDarkThemeChange = themeViewModel::onDarkThemeChange
                        )

                    }
                }
            }
        }
    }
}

