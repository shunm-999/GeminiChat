package com.shunm.view.chat.screen

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.shunm.common_compose.layouts.GeminiScaffold
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.view.chat.layouts.ChatNavigationDrawer
import com.shunm.view.chat.layouts.CreateThreadButton
import com.shunm.view.chat.layouts.NavigationItem

@Composable
internal fun ChatScreen() {
    ChatNavigationDrawer(
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
        GeminiScaffold {
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
