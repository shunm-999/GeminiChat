package com.shunm.view.chat.uiState

import android.graphics.Bitmap
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.ThreadDetail

internal sealed interface ChatUiState {
    data object Loading : ChatUiState

    data object Error : ChatUiState

    sealed interface Reedy : ChatUiState {
        data object Initial : Reedy

        data class Content(
            val threadDetail: ThreadDetail,
        ) : Reedy
    }
}

internal val ChatUiState.messageList: List<Message>
    get() = (this as? ChatUiState.Reedy.Content)?.threadDetail?.messages.orEmpty()

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
