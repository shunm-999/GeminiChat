package com.shunm.geminichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shunm.commonCompose.theme.GeminiChatTheme
import com.shunm.geminichat.ui.GeminiChatApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeminiChatTheme {
                GeminiChatApp()
            }
        }
    }
}
