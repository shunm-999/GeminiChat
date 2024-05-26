package com.shunm.view.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
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

fun NavController.navigateToChat(route: ChatRoute) =
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
    }

fun NavGraphBuilder.chatNavGraph(navController: NavController) {
    composable<ChatRoute> {
        ChatScreen(
            navigate = { route ->
                when (route) {
                    is ChatRoute -> navController.navigateToChat(route)
                }
            },
        )
    }
}
