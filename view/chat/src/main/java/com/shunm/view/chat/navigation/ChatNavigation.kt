package com.shunm.view.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shunm.common_compose.navigation.NavigateRoute
import com.shunm.common_compose.navigation.NavigateTemplate
import com.shunm.view.chat.screen.ChatScreen
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoute(
    val threadId: Long,
) : NavigateRoute {
    @Serializable
    companion object : NavigateTemplate {
        override fun toRoute(): Any = ChatRoute(threadId = -1)
    }
}

fun NavController.navigateToChat(route: ChatRoute) = navigate(route)

fun NavGraphBuilder.chatNavGraph(navController: NavController) {
    composable<ChatRoute> {
        ChatScreen(
            navigate = { route ->
                navController.navigate(route)
            },
        )
    }
}
