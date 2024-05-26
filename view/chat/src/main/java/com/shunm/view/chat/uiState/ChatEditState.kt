package com.shunm.view.chat.uiState

internal sealed interface ChatEditState {
    data object NewThread : ChatEditState

    data class EditThread(
        val thread: Thread,
    ) : ChatEditState
}
