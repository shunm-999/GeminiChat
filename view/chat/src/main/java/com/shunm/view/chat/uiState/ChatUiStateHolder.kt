package com.shunm.view.chat.uiState

import android.graphics.Bitmap
import com.shunm.domain.chat.model.Thread

internal sealed interface ChatUiState {
    data object Loading : ChatUiState

    data object Error : ChatUiState

    sealed interface Reedy : ChatUiState {
        data object Initial : Reedy

        data class Content(
            val thread: Thread,
        ) : Reedy
    }
}

internal data class ChatInputUiState(
    val text: String,
    val image: Bitmap? = null,
) {
    companion object {
        val Empty = ChatInputUiState("", null)
    }
}

internal interface ChatUiStateHolder {
    val uiState: ChatUiState

    val inputUiState: ChatInputUiState

    fun update(updater: ChatInputUiState.() -> ChatInputUiState)

    fun submit()
}
