package com.shunm.view.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shunm.view.chat.screen.ChatScreen
import kotlinx.serialization.Serializable

@Serializable
object ChatNavigation

fun NavController.navigateToChat(route: ChatNavigation) = navigate(route)

fun NavGraphBuilder.chatNavGraph() {
    composable<ChatNavigation> {
        ChatScreen()
    }
}
