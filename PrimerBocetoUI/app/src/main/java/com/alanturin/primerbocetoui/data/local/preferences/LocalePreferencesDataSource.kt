package com.alanturin.primerbocetoui.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalePreferencesDataSource @Inject constructor(
    private val preferencesDataStore: DataStore<Preferences>
) {
    private val languageTagKey = stringPreferencesKey("app_language_tag")

    val languageTag: Flow<String> = preferencesDataStore.data.map { preferences ->
        preferences[languageTagKey] ?: DEFAULT_LANGUAGE_TAG
    }
    suspend fun setLanguageTag(tag: String) {
        preferencesDataStore.edit { preferences ->
            preferences[languageTagKey] = tag
        }
    }
    companion object {
        const val DEFAULT_LANGUAGE_TAG = "es"
    }
}
