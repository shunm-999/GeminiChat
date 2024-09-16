package com.shunm.view.chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import com.shunm.commonCompose.navigation.GeminiChatNavGraphBuilder
import com.shunm.commonCompose.navigation.NavGraph
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.navigation.StartDestination
import com.shunm.commonCompose.navigation.composable
import com.shunm.commonCompose.navigation.hiltNavGraphViewModel
import com.shunm.commonCompose.navigation.navigateSingleTop
import com.shunm.commonCompose.navigation.navigation
import com.shunm.commonCompose.navigation.startDestinationWithArgs
import com.shunm.domain.chat.model.ThreadId
import com.shunm.view.chat.screen.ChatScreen
import com.shunm.view.chat.viewmodel.ChatViewModelFactory
import com.shunm.view.chat.viewmodel.DrawerViewModel
import kotlinx.serialization.Serializable

@Serializable
object ChatNavGraph : NavGraph {
    override val startDestination: StartDestination = startDestinationWithArgs {
        ChatRoute(threadId = -1)
    }
}

@Serializable
data class ChatRoute internal constructor(
    val threadId: Long,
) : NavigateRoute

fun GeminiChatNavGraphBuilder.chatNavGraph() {
    navigation<ChatNavGraph>(
        startDestination = ChatNavGraph.startDestination,
    ) {
        composable<ChatRoute> { _, chatRoute ->
            val drawerViewModel: DrawerViewModel =
                hiltNavGraphViewModel(
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
                        is ChatRoute -> navController.navigateSingleTop(route)
                    }
                },
            )
        }
    }
}
