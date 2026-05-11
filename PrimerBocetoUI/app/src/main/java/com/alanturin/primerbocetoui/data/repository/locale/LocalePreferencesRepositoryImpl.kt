package com.alanturin.primerbocetoui.data.repository.locale

import com.alanturin.primerbocetoui.data.local.preferences.LocalePreferencesDataSource
import com.alanturin.primerbocetoui.domain.repository.LocalePreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalePreferencesRepositoryImpl @Inject constructor(
    private val localePreferencesDataSource: LocalePreferencesDataSource
) : LocalePreferencesRepository {

    override val languageTag: Flow<String> = localePreferencesDataSource.languageTag
    override suspend fun setLanguageTag(tag: String) {
        localePreferencesDataSource.setLanguageTag(tag)
    }
}
