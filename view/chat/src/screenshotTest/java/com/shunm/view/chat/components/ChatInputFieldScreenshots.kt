package com.shunm.view.chat.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.shunm.common_compose.theme.GeminiChatTheme

class ChatInputFieldScreenshots {
    @Preview(showBackground = true)
    @Composable
    fun ChatInputFieldPreview() {
        GeminiChatTheme {
            ChatInputField(
                text = "text",
                onTextChange = {
                },
                onSubmit = {
                },
            )
        }
    }
}
