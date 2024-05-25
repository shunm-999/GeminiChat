package com.shunm.geminichat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.geminichat.ui.GeminiChatApp

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
