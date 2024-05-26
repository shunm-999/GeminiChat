package com.shunm.view.chat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.shunm.common_compose.navigation.NavigateRoute
import com.shunm.view.chat.screen.ChatScreen
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoute(
    val threadId: Long = -1,
) : NavigateRoute

fun NavController.navigateToChat(route: ChatRoute) =
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
    }

fun NavGraphBuilder.chatNavGraph(navController: NavController) {
    composable<ChatRoute> { backStackEntry ->
        ChatScreen(
            navigate = { route ->
                when (route) {
                    is ChatRoute -> navController.navigateToChat(route)
                }
            },
        )
    }
}
