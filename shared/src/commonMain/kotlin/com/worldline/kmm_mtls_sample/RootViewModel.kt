package com.worldline.kmm_mtls_sample

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.core.component.KoinComponent

abstract class RootViewModel<S, E>(initialState: S) : KoinComponent, PlatformViewModel() {
    protected val _uiState = MutableStateFlow(initialState)

    val state: StateFlow<S> = _uiState

    fun onEach(newState: ((S) -> Unit)) {
        state.onEach {
            newState(it)
        }.launchIn(vmScope)
    }
}

open class ViewState