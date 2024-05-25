package com.shunm.geminichat.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shunm.geminichat.ui.navigation.GeminiChatNavHost

@Composable
internal fun GeminiChatApp(modifier: Modifier = Modifier) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(
            modifier = modifier.padding(innerPadding),
        ) {
            GeminiChatNavHost(
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
