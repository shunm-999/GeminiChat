package com.shunm.view.chat.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.shunm.common_compose.layouts.GeminiScaffold
import com.shunm.common_compose.navigation.NavigateRoute
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.common_compose.util.rememberPhotoPicker
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Ok
import com.shunm.view.chat.R
import com.shunm.view.chat.components.ChatInputField
import com.shunm.view.chat.components.MessageList
import com.shunm.view.chat.layouts.ChatNavigationDrawerContentScope
import com.shunm.view.chat.layouts.ChatNavigationLayout
import com.shunm.view.chat.layouts.CreateThreadButton
import com.shunm.view.chat.layouts.DrawerContentScope
import com.shunm.view.chat.layouts.NavigationItem
import com.shunm.view.chat.navigation.ChatRoute
import com.shunm.view.chat.uiState.ChatUiStateHolder
import com.shunm.view.chat.uiState.DrawerUiStateHolder
import com.shunm.view.chat.uiState.messageList
import com.shunm.view.chat.viewmodel.ChatViewModel
import com.shunm.view.chat.viewmodel.ChatViewModelFactory
import kotlinx.coroutines.launch

@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel,
    drawerUiStateHolder: DrawerUiStateHolder,
    navigate: (NavigateRoute) -> Unit,
) {
    ChatScreen(
        uiStateHolder = viewModel,
        drawerUiStateHolder = drawerUiStateHolder,
        navigate = navigate,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen(
    uiStateHolder: ChatUiStateHolder,
    drawerUiStateHolder: DrawerUiStateHolder,
    navigate: (NavigateRoute) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ChatNavigationLayout(
        drawerState = drawerState,
        drawerContent = {
            chatDrawerSheet(
                closeDrawer = { afterCloseDrawer ->
                    coroutineScope.launch {
                        drawerState.close()
                        afterCloseDrawer()
                    }
                },
                navigate = navigate,
                drawerUiStateHolder = drawerUiStateHolder,
            )
        },
    ) {
        GeminiScaffold(
            topBar = {
                ChatTopBar(
                    openDrawer = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                )
            },
            bottomBar = {
                ChatBottomBar(
                    uiStateHolder = uiStateHolder,
                )
            },
        ) {
            ChatScreenContent(
                uiStateHolder = uiStateHolder,
            )
        }
    }
}

private fun DrawerContentScope.chatDrawerSheet(
    closeDrawer: (() -> Unit) -> Unit,
    navigate: (NavigateRoute) -> Unit,
    drawerUiStateHolder: DrawerUiStateHolder,
) {
    header {
        CreateThreadButton {
            closeDrawer {
                navigate(
                    ChatRoute(
                        threadId = -1,
                    ),
                )
            }
        }
    }
    with(drawerUiStateHolder) {
        uiState.threadList.forEach { thread ->
            content {
                NavigationItem(
                    modifier = Modifier.fillMaxWidth(),
                    isSelected = thread.id == uiState.currentThread?.id,
                    text = thread.title,
                    onClick = {
                        selectThread(thread)
                        navigate(
                            ChatRoute(
                                threadId = thread.id.value,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChatNavigationDrawerContentScope.ChatTopBar(openDrawer: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.view_chat_gemini_model_name),
            )
        },
        navigationIcon = {
            if (needNavigationIcon) {
                IconButton(onClick = openDrawer) {
                    Icon(
                        imageVector = Icons.Default.DensityMedium,
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
private fun ChatBottomBar(uiStateHolder: ChatUiStateHolder) {
    val coroutineScope = rememberCoroutineScope()
    val photoPicker = rememberPhotoPicker()

    BottomAppBar(
        modifier = Modifier.imePadding(),
    ) {
        ChatInputField(
            text = uiStateHolder.inputUiState.text,
            onTextChange = { text ->
                uiStateHolder.update {
                    copy(text = text)
                }
            },
            imageList = uiStateHolder.inputUiState.imageList,
            onImageListChange = { imageList ->
                uiStateHolder.update {
                    copy(imageList = imageList)
                }
            },
            onSubmit = {
                uiStateHolder.submit()
            },
            optionVisible = uiStateHolder.inputUiState.optionVisible,
            optionVisibleChange = { visible ->
                uiStateHolder.update {
                    copy(optionVisible = visible)
                }
            },
            onClickPhoto = {
                coroutineScope.launch {
                    when (val result = photoPicker.pickSingleMedia()) {
                        is Err -> {
                        }
                        is Ok -> {
                        }
                    }
                }
            },
        )
    }
}

@Composable
private fun ChatScreenContent(
    modifier: Modifier = Modifier,
    uiStateHolder: ChatUiStateHolder,
) {
    MessageList(
        modifier = modifier,
        messages = uiStateHolder.uiState.messageList,
    )
}

@PreviewLightDark
@Composable
internal fun ChatScreenPreview() {
    GeminiChatTheme {
        Surface {
            ChatScreen(
                hiltViewModel(
                    creationCallback = { factory: ChatViewModelFactory ->
                        factory.create(ThreadId(1))
                    },
                ),
                drawerUiStateHolder = DrawerUiStateHolder(),
                navigate = {
                },
            )
        }
    }
}
