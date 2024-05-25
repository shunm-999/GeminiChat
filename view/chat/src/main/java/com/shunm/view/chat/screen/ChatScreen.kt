package com.shunm.view.chat.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.shunm.common_compose.layouts.GeminiScaffold
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.view.chat.R
import com.shunm.view.chat.layouts.ChatNavigationDrawer
import com.shunm.view.chat.layouts.CreateThreadButton
import com.shunm.view.chat.layouts.NavigationItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChatScreen() {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ChatNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            header {
                CreateThreadButton {
                }
            }
            listOf("Thread1", "Thread2", "Thread3").forEach { thread ->
                content {
                    NavigationItem(
                        text = thread,
                        onClick = {
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
            ChatScreenContent()
        }
    }
}

@Composable
private fun ChatScreenContent() {
}

@PreviewLightDark
@Composable
internal fun ChatScreenPreview() {
    GeminiChatTheme {
        Surface {
            ChatScreen()
        }
    }
}
