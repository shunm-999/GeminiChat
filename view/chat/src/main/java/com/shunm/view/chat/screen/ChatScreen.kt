package com.shunm.view.chat.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DensityMedium
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
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
import com.shunm.view.chat.R
import com.shunm.view.chat.components.MessageList
import com.shunm.view.chat.layouts.ChatNavigationDrawer
import com.shunm.view.chat.layouts.CreateThreadButton
import com.shunm.view.chat.layouts.NavigationItem
import com.shunm.view.chat.navigation.ChatRoute
import com.shunm.view.chat.uiState.ChatUiStateHolder
import com.shunm.view.chat.uiState.messageList
import com.shunm.view.chat.viewmodel.ChatViewModel
import kotlinx.coroutines.launch

@Composable
internal fun ChatScreen(
    viewModel: ChatViewModel = hiltViewModel(),
    navigate: (NavigateRoute) -> Unit,
) {
    ChatScreen(
        uiStateHolder = viewModel,
        navigate = navigate,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen(
    uiStateHolder: ChatUiStateHolder,
    navigate: (NavigateRoute) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ChatNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            header {
                CreateThreadButton {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navigate(
                        ChatRoute(
                            threadId = -1,
                        ),
                    )
                }
            }
            uiStateHolder.uiState.messageList.forEach { thread ->
                content {
                    NavigationItem(
                        text = thread.text,
                        onClick = {
                            navigate(
                                ChatRoute(
                                    threadId = thread.id.value,
                                ),
                            )
                        },
                    )
                }
            }
        },
    ) {
        GeminiScaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.view_chat_gemini_model_name),
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.DensityMedium,
                                contentDescription = null,
                            )
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
            },
        ) {
            ChatScreenContent(
                uiStateHolder = uiStateHolder,
            )
        }
    }
}

@Composable
private fun ChatScreenContent(
    modifier: Modifier = Modifier,
    uiStateHolder: ChatUiStateHolder,
) {
    Box(
        modifier = modifier,
    ) {
        MessageList(
            messages = uiStateHolder.uiState.messageList,
        )
    }
}

@PreviewLightDark
@Composable
internal fun ChatScreenPreview() {
    GeminiChatTheme {
        Surface {
            ChatScreen(
                navigate = {
                },
            )
        }
    }
}
