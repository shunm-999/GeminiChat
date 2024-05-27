package com.shunm.view.chat.uiState

import com.shunm.domain.chat.model.ThreadSummary

data class DrawerUiState(
    val threadList: List<ThreadSummary>,
)

internal interface DrawerUiStateHolder {
    val uiState: DrawerUiState
}

internal fun DrawerUiStateHolder(): DrawerUiStateHolder =
    object : DrawerUiStateHolder {
        override val uiState: DrawerUiState
            get() = DrawerUiState(emptyList())
    }
