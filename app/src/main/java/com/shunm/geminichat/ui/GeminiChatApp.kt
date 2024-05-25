package com.shunm.geminichat.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shunm.geminichat.ui.navigation.GeminiChatNavHost

@Composable
internal fun GeminiChatApp(modifier: Modifier = Modifier) {
    GeminiChatNavHost(
        modifier = modifier.fillMaxSize(),
    )
}
