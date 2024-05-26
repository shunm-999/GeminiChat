package com.shunm.view.chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.shunm.common_compose.navigation.NavigateRoute
import com.shunm.domain.chat.model.ThreadId
import com.shunm.view.chat.screen.ChatScreen
import com.shunm.view.chat.viewmodel.ChatViewModelFactory
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
        val chatRoute = backStackEntry.toRoute<ChatRoute>()
        ChatScreen(
            viewModel =
                hiltViewModel(
                    creationCallback = { factory: ChatViewModelFactory ->
                        factory.create(ThreadId(chatRoute.threadId))
                    },
                ),
            navigate = { route ->
                when (route) {
                    is ChatRoute -> navController.navigateToChat(route)
                }
            },
        )
    }
}
