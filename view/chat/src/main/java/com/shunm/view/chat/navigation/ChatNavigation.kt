package com.shunm.view.chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import com.shunm.commonCompose.navigation.GeminiChatNavGraphBuilder
import com.shunm.commonCompose.navigation.NavGraph
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.navigation.NavigationRouteTemplate
import com.shunm.commonCompose.navigation.composable
import com.shunm.commonCompose.navigation.hiltNavGraphViewModel
import com.shunm.commonCompose.navigation.navigation
import com.shunm.domain.chat.model.ThreadId
import com.shunm.view.chat.screen.ChatScreen
import com.shunm.view.chat.viewmodel.ChatViewModelFactory
import com.shunm.view.chat.viewmodel.DrawerViewModel
import kotlinx.serialization.Serializable

@Serializable
object ChatNavGraph : NavGraph, NavigationRouteTemplate.NoArgs

@Serializable
data class ChatRoute internal constructor(
    val threadId: Long,
) : NavigateRoute {

    companion object : NavigationRouteTemplate.WithArgs {
        override fun toRoute(): Any {
            return ChatRoute(threadId = -1)
        }
    }
}

private fun NavController.navigateToChat(route: ChatRoute) =
    navigate(route) {
        popUpTo<ChatRoute> {
            inclusive = true
        }
    }

fun GeminiChatNavGraphBuilder.chatNavGraph() {
    navigation<ChatNavGraph>(
        startDestination = ChatRoute,
    ) {
        composable<ChatRoute> { _, chatRoute ->
            val drawerViewModel: DrawerViewModel =
                hiltNavGraphViewModel(
                    navController = navController,
                    navGraph = ChatNavGraph,
                )
            ChatScreen(
                viewModel = hiltViewModel(
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
