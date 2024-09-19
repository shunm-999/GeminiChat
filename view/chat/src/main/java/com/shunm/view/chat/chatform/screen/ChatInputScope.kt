package com.shunm.view.chat.chatform.screen

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shunm.view.chat.chatform.uiState.ChatUiStateHolder

@Composable
internal fun ChatInputScope(
    stateHolder: ChatUiStateHolder,
    content: @Composable ChatInputScope.() -> Unit,
) {
    val scope =
        remember(stateHolder) {
            ChatInputScope(stateHolder)
        }
    with(scope) {
        content()
    }
}

internal data class ChatInputScope(
    private val stateHolder: ChatUiStateHolder,
) {
    var text: String
        get() = stateHolder.inputUiState.text
        set(value) {
            stateHolder.update {
                copy(text = value)
            }
        }

    var optionVisible: Boolean
        get() = stateHolder.inputUiState.optionVisible
        set(value) {
            stateHolder.update {
                copy(optionVisible = value)
            }
        }

    var imageList: List<Uri>
        get() = stateHolder.inputUiState.imageList
        set(value) {
            stateHolder.update {
                copy(imageList = value)
            }
        }

    fun submit() {
        stateHolder.submit()
    }
}
