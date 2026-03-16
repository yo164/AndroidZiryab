package com.alanturin.primerbocetoui.ui.session

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JustificacionSessionService @Inject constructor() {

    private val _uri = MutableStateFlow<String?>(null)
    val uri: StateFlow<String?> = _uri.asStateFlow()

    private val _idAssistance = MutableStateFlow<Int?>(null)
    val idAssistance: StateFlow<Int?> = _idAssistance.asStateFlow()

    fun set(uri: String, idAssistance: Int) {
        _uri.value = uri
        _idAssistance.value = idAssistance
    }

    fun clear() {
        _uri.value = null
        _idAssistance.value = null
    }
}