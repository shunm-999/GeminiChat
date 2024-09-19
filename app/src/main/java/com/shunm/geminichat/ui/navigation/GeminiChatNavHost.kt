package com.shunm.geminichat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.shunm.commonCompose.navigation.GeminiChatNavHost
import com.shunm.commonCompose.navigation.startDestinationNoArgs
import com.shunm.view.chat.chatform.ChatNavGraph
import com.shunm.view.chat.chatform.chatNavGraph

@Composable
internal fun GeminiChatNavHost(modifier: Modifier = Modifier) {
    GeminiChatNavHost(
        modifier = modifier,
        navController = rememberNavController(),
        startDestination = startDestinationNoArgs<ChatNavGraph>(),
    ) {
        chatNavGraph()
    }
}
