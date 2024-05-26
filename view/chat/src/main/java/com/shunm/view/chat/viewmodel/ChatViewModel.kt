package com.shunm.view.chat.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shunm.domain.chat.input_data.MessageCreation
import com.shunm.domain.chat.input_data.ThreadCreation
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.usecase.CreateMessageUseCase
import com.shunm.domain.chat.usecase.CreateThreadUseCase
import com.shunm.domain.chat.usecase.GetMessageListUseCase
import com.shunm.domain.chat.usecase.GetThreadDetailUseCase
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Ok
import com.shunm.view.chat.uiState.ChatInputUiState
import com.shunm.view.chat.uiState.ChatUiState
import com.shunm.view.chat.uiState.ChatUiStateHolder
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

@HiltViewModel(assistedFactory = ChatViewModelFactory::class)
internal class ChatViewModel
    @AssistedInject
    constructor(
        @Assisted private val threadId: ThreadId,
        private val getThreadDetailUseCase: GetThreadDetailUseCase,
        private val getMessageListUseCase: GetMessageListUseCase,
        private val createThreadUseCase: CreateThreadUseCase,
        private val createMessageUseCase: CreateMessageUseCase,
    ) : ViewModel(), ChatUiStateHolder {
        override var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
            private set
        override var inputUiState: ChatInputUiState by mutableStateOf(ChatInputUiState.Empty)
            private set

        init {
            loadThread(threadId)
        }

        private fun loadThread(threadId: ThreadId) {
            if (threadId.value == -1L) {
                uiState = ChatUiState.Reedy.Initial
            } else {
                viewModelScope.launch {
                    getThreadDetailUseCase(threadId).collectLatest {
                        uiState =
                            when (it) {
                                is Ok -> {
                                    ChatUiState.Reedy.Content(
                                        threadDetail = it.value,
                                    )
                                }

                                is Err -> ChatUiState.Error
                            }
                    }
                }
            }
        }

        override fun update(updater: ChatInputUiState.() -> ChatInputUiState) {
            inputUiState = updater(inputUiState)
        }

        override fun submit() {
            val currentUiState = (uiState as? ChatUiState.Reedy) ?: return
            viewModelScope.launch {
                val threadId =
                    when (currentUiState) {
                        ChatUiState.Reedy.Initial -> {
                            when (
                                val result =
                                    createThreadUseCase(
                                        ThreadCreation(
                                            title = "Chat",
                                            createAt = Clock.System.now(),
                                        ),
                                    )
                            ) {
                                is Ok -> {
                                    loadThread(result.value)
                                    result.value
                                }

                                is Err -> {
                                    uiState = ChatUiState.Error
                                    return@launch
                                }
                            }
                        }
                        is ChatUiState.Reedy.Content -> {
                            currentUiState.threadDetail.id
                        }
                    }
                val messageCreation =
                    MessageCreation(
                        threadId = threadId,
                        sender =
                            Message.Sender.User(
                                user =
                                    com.shunm.domain.chat.model.User(
                                        name = "User",
                                    ),
                            ),
                        text = inputUiState.text,
                        image = inputUiState.image,
                        createAt = Clock.System.now(),
                    )
                createMessageUseCase(messageCreation)

                inputUiState = ChatInputUiState.Empty
            }
        }
    }

@AssistedFactory
internal interface ChatViewModelFactory {
    fun create(threadId: ThreadId): ChatViewModel
}
