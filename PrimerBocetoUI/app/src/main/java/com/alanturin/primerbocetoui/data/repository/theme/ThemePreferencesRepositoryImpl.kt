package com.alanturin.primerbocetoui.data.repository.theme

import com.alanturin.primerbocetoui.data.local.preferences.ThemePreferencesDataSource
import com.alanturin.primerbocetoui.domain.repository.ThemePreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemePreferencesRepositoryImpl @Inject constructor(
    private val themePreferencesDataSource: ThemePreferencesDataSource
) : ThemePreferencesRepository {

    override val isDarkTheme: Flow<Boolean> = themePreferencesDataSource.isDarkTheme

    override suspend fun setDarkTheme(enabled: Boolean) {
        themePreferencesDataSource.setDarkTheme(enabled)
    }
}
