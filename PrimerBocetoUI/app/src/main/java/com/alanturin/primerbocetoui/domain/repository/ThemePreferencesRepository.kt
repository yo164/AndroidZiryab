package com.alanturin.primerbocetoui.domain.repository

import kotlinx.coroutines.flow.Flow

interface ThemePreferencesRepository {
    val isDarkTheme: Flow<Boolean>
    suspend fun setDarkTheme(enabled: Boolean)
}
