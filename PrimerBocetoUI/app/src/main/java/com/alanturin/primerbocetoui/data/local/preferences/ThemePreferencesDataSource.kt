package com.alanturin.primerbocetoui.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferencesDataSource @Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>
) {
    private val darkThemeKey = booleanPreferencesKey("dark_theme_enabled")

    val isDarkTheme: Flow<Boolean> = preferencesDataStore.data.map { preferences ->
        preferences[darkThemeKey] ?: false
    }

    suspend fun setDarkTheme(enabled: Boolean) {
        preferencesDataStore.edit { preferences ->
            preferences[darkThemeKey] = enabled
        }
    }
}
