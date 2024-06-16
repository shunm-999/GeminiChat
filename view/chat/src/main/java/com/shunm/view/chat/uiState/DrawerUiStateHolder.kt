package com.shunm.view.chat.uiState

import com.shunm.domain.chat.model.ThreadSummary

data class DrawerUiState(
    val currentThread: ThreadSummary?,
    val threadList: List<ThreadSummary>,
)

internal interface DrawerUiStateHolder {
    val uiState: DrawerUiState

    fun selectThread(thread: ThreadSummary)
}

internal fun DrawerUiStateHolder(): DrawerUiStateHolder =
    object : DrawerUiStateHolder {
        override val uiState: DrawerUiState
            get() =
                DrawerUiState(
                    currentThread = null,
                    threadList = emptyList(),
                )

        override fun selectThread(thread: ThreadSummary) {
        }
    }
