package com.alanturin.primerbocetoui.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalePreferencesRepository {
    val languageTag: Flow<String>

    suspend fun setLanguageTag(tag: String)
}
