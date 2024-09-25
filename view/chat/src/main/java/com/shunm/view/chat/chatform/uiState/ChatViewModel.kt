package com.shunm.view.chat.chatform.uiState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shunm.commonCompose.navigation.ext.toNavigateRoute
import com.shunm.domain.chat.inputData.MessageCreation
import com.shunm.domain.chat.inputData.ThreadCreation
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.usecase.CreateMessageUseCase
import com.shunm.domain.chat.usecase.CreateThreadUseCase
import com.shunm.domain.chat.usecase.GetThreadDetailUseCase
import com.shunm.domain.common.ext.format
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Ok
import com.shunm.view.chat.chatform.ChatRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
internal class ChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getThreadDetailUseCase: GetThreadDetailUseCase,
    private val createThreadUseCase: CreateThreadUseCase,
    private val createMessageUseCase: CreateMessageUseCase,
) : ViewModel(), ChatUiStateHolder {
    private val route: ChatRoute by savedStateHandle.toNavigateRoute<ChatRoute, ChatRoute.Args>()

    override var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set
    override var inputUiState: ChatInputUiState by mutableStateOf(ChatInputUiState.Empty)
        private set

    init {
        initialize(route.args.threadId)
    }

    private fun initialize(threadId: ThreadId) {
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
                        val datetimeString = Clock.System.now().format("yyyy-MM-dd")

                        val result =
                            createThreadUseCase(
                                ThreadCreation(
                                    title = "Chat $datetimeString",
                                    createAt = Clock.System.now(),
                                ),
                            )
                        when (result) {
                            is Ok -> {
                                initialize(result.value)
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
                    imageList = inputUiState.imageList,
                    createAt = Clock.System.now(),
                )
            createMessageUseCase(messageCreation)

            inputUiState = ChatInputUiState.Empty
        }
    }
}
