package com.shunm.view.chat.chatform.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import com.shunm.commonCompose.components.DropdownMenu
import com.shunm.commonCompose.components.DropdownMenuItem
import com.shunm.commonCompose.layouts.GeminiScaffold
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.theme.GeminiChatTheme
import com.shunm.commonCompose.util.rememberDocumentMediaSelector
import com.shunm.commonCompose.util.rememberPhotoPicker
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Ok
import com.shunm.view.camera.util.LocalCameraManager
import com.shunm.view.chat.R
import com.shunm.view.chat.chatform.ChatRoute
import com.shunm.view.chat.chatform.model_detail.ModelDetailRoute
import com.shunm.view.chat.chatform.uiState.ChatUiStateHolder
import com.shunm.view.chat.chatform.uiState.ChatViewModel
import com.shunm.view.chat.chatform.uiState.messageList
import com.shunm.view.chat.components.ChatInputField
import com.shunm.view.chat.components.MessageList
import com.shunm.view.chat.layouts.ChatNavigationDrawerContentScope
import com.shunm.view.chat.layouts.ChatNavigationLayout
import com.shunm.view.chat.layouts.CreateThreadButton
import com.shunm.view.chat.layouts.DrawerContentScope
import com.shunm.view.chat.layouts.NavigationItem
import com.shunm.view.chat.uiState.DrawerUiStateHolder
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
                    navigate = navigate,
                )
            },
        ) {
            Column {
                ChatScreenContent(
                    modifier = Modifier.weight(1f),
                    uiStateHolder = uiStateHolder,
                )
                ChatInputScope(
                    stateHolder = uiStateHolder,
                ) {
                    ChatBottomBar()
                }
            }
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
                    ChatRoute.newChat(),
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
                            ChatRoute.withArgs(thread.id),
                        )
                    },
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChatNavigationDrawerContentScope.ChatTopBar(
    openDrawer: () -> Unit,
    navigate: (NavigateRoute) -> Unit,
) {
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
            var menuExpanded by remember {
                mutableStateOf(false)
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                )
            }
            IconButton(onClick = {
                menuExpanded = true
            }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                )
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = {
                    menuExpanded = false
                }
            ) {
                item {
                    DropdownMenuItem(
                        text = "詳細を表示する",
                        onClick = {
                            navigate(ModelDetailRoute)
                        }
                    )
                }
            }
        },
    )
}

@Composable
private fun ChatInputScope.ChatBottomBar() {
    val coroutineScope = rememberCoroutineScope()
    val photoPicker = rememberPhotoPicker()
    val documentMediaSelector = rememberDocumentMediaSelector()
    val cameraManager = LocalCameraManager.current

    ChatInputField(
        modifier = Modifier.imePadding(),
        text = text,
        onTextChange = { newText ->
            text = newText
        },
        imageList = imageList,
        onImageListChange = { newImageList ->
            imageList = newImageList
        },
        onSubmit = {
            submit()
        },
        optionVisible = optionVisible,
        optionVisibleChange = { visible ->
            optionVisible = visible
        },
        onClickPhoto = {
            coroutineScope.launch {
                when (val result = photoPicker.pickMedia()) {
                    is Err -> {
                    }

                    is Ok -> {
                        imageList = imageList + result.value
                    }
                }
            }
        },
        onClickFolder = {
            coroutineScope.launch {
                when (val result = documentMediaSelector.selectMedia()) {
                    is Err -> {
                    }

                    is Ok -> {
                        imageList = imageList + result.value
                    }
                }
            }
        },
        onClickCamera = {
            coroutineScope.launch {
                when (val result = cameraManager.launchCamera()) {
                    is Err -> {
                    }

                    is Ok -> {
                        imageList = imageList + result.value
                    }
                }
            }
        },
    )
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
                hiltViewModel(),
                drawerUiStateHolder = DrawerUiStateHolder(),
                navigate = {
                },
            )
        }
    }
}
