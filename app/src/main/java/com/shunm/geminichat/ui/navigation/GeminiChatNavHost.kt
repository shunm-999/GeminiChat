package com.shunm.geminichat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shunm.view.chat.navigation.ChatNavigation
import com.shunm.view.chat.navigation.chatNavGraph

@Composable
internal fun GeminiChatNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ChatNavigation,
        modifier = modifier,
    ) {
        chatNavGraph()
    }
}
