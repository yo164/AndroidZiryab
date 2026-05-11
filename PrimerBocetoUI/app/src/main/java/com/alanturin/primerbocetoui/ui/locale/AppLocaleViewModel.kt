package com.alanturin.primerbocetoui.ui.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.data.local.preferences.LocalePreferencesDataSource
import com.alanturin.primerbocetoui.domain.repository.LocalePreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class AppLocaleViewModel @Inject constructor(
    private val localePreferencesRepository: LocalePreferencesRepository
) : ViewModel() {

    val languageTag: StateFlow<String> = localePreferencesRepository.languageTag.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LocalePreferencesDataSource.DEFAULT_LANGUAGE_TAG
    )

    fun onLanguageTagSelected(tag: String) {
        if (tag == languageTag.value) return
        viewModelScope.launch {
            localePreferencesRepository.setLanguageTag(tag)
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tag))
        }
    }
}
