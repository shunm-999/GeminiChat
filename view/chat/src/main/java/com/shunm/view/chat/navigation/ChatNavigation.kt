package com.shunm.view.chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.shunm.commonCompose.navigation.NavGraph
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.navigation.hiltNavGraphViewModel
import com.shunm.domain.chat.model.ThreadId
import com.shunm.view.chat.screen.ChatScreen
import com.shunm.view.chat.viewmodel.ChatViewModelFactory
import com.shunm.view.chat.viewmodel.DrawerViewModel
import kotlinx.serialization.Serializable

@Serializable
object ChatNavGraph : NavGraph

@Serializable
data class ChatRoute internal constructor(
    val threadId: Long = -1,
) : NavigateRoute

fun NavController.navigateToChat(route: ChatRoute) =
    navigate(route) {
        popUpTo<ChatRoute> {
            inclusive = true
        }
    }

fun NavGraphBuilder.chatNavGraph(navController: NavController) {
    navigation<ChatNavGraph>(
        startDestination = ChatRoute::class,
    ) {
        composable<ChatRoute> { backStackEntry ->
            val chatRoute = backStackEntry.toRoute<ChatRoute>()
            val drawerViewModel: DrawerViewModel =
                hiltNavGraphViewModel(
                    navController = navController,
                    navGraph = ChatNavGraph,
                )
            ChatScreen(
                viewModel =
                hiltViewModel(
                    creationCallback = { factory: ChatViewModelFactory ->
                        factory.create(ThreadId(chatRoute.threadId))
                    },
                ),
                drawerUiStateHolder = drawerViewModel,
                navigate = { route ->
                    when (route) {
                        is ChatRoute -> navController.navigateToChat(route)
                    }
                },
            )
        }
    }
}
