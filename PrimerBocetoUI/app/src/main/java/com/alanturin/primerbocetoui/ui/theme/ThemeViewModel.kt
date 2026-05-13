package com.alanturin.primerbocetoui.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alanturin.primerbocetoui.domain.repository.ThemePreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val themePreferencesRepository: ThemePreferencesRepository
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = themePreferencesRepository.isDarkTheme.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun onDarkThemeChange(enabled: Boolean) {
        viewModelScope.launch {
            themePreferencesRepository.setDarkTheme(enabled)
        }
    }
}
