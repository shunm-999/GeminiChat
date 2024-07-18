package com.shunm.geminichat.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shunm.geminichat.ui.navigation.GeminiChatNavHost
import com.shunm.view.camera.layout.WithCamera

@Composable
internal fun GeminiChatApp(modifier: Modifier = Modifier) {
    WithCamera {
        GeminiChatNavHost(
            modifier = modifier.fillMaxSize(),
        )
    }
}
