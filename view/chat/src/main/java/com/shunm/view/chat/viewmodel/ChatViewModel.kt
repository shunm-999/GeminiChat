package com.shunm.view.chat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.shunm.domain.chat.model.Message
import com.shunm.view.chat.uiState.ChatInputUiState
import com.shunm.view.chat.uiState.ChatUiState
import com.shunm.view.chat.uiState.ChatUiStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel(), ChatUiStateHolder {
        private val messageList = mutableListOf<Message>()

        override var uiState: ChatUiState by mutableStateOf(ChatUiState.Reedy.Initial)
            private set
        override var inputUiState: ChatInputUiState by mutableStateOf(ChatInputUiState.Empty)
            private set

        override fun update(updater: ChatInputUiState.() -> ChatInputUiState) {
            inputUiState = updater(inputUiState)
        }

        override fun submit() {
//            val message =
//                Message(
//                    id = MessageId(0),
//                    sender =
//                        Message.Sender.User(
//                            user =
//                                User(
//                                    name = "User",
//                                ),
//                        ),
//                    text = inputUiState.text,
//                    image = inputUiState.image,
//                    createAt = Clock.System.now(),
//                )
//            messageList.add(message)
//
//            uiState =
//                ChatUiState.Reedy.Content(
//                    threadSummary =
//                        ThreadSummary(
//                            title = "Chat",
//                            messages = messageList,
//                        ),
//                )
//
//            inputUiState = ChatInputUiState.Empty
        }
    }
