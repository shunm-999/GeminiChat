package com.shunm.view.chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import com.shunm.commonCompose.navigation.GeminiChatNavGraphBuilder
import com.shunm.commonCompose.navigation.NavGraph
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.navigation.NavigateRouteArgs
import com.shunm.commonCompose.navigation.StartDestination
import com.shunm.commonCompose.navigation.composable
import com.shunm.commonCompose.navigation.ext.hiltNavGraphViewModel
import com.shunm.commonCompose.navigation.ext.navigateSingleTop
import com.shunm.commonCompose.navigation.navigation
import com.shunm.commonCompose.navigation.startDestinationWithArgs
import com.shunm.domain.chat.model.ThreadId
import com.shunm.view.chat.screen.ChatScreen
import com.shunm.view.chat.viewmodel.DrawerViewModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
object ChatNavGraph : NavGraph {
    override val startDestination: StartDestination<ChatRoute>
        get() = startDestinationWithArgs { ChatRoute.newChat() }
}

@Parcelize
@Serializable
data class ChatRoute internal constructor(
    override val args: Args,
) : NavigateRoute.WithArgs<ChatRoute.Args> {

    @Parcelize
    @Serializable
    data class Args(
        val threadId: ThreadId,
    ) : NavigateRouteArgs

    companion object {
        fun withArgs(threadId: ThreadId): ChatRoute = ChatRoute(args = Args(threadId = threadId))

        fun newChat(): ChatRoute = ChatRoute(args = Args(threadId = ThreadId.undefined))
    }
}

fun GeminiChatNavGraphBuilder.chatNavGraph() {
    navigation<ChatNavGraph, ChatRoute, ChatRoute.Args>(
        startDestination = ChatNavGraph.startDestination,
    ) {
        composable<ChatRoute, ChatRoute.Args> { _, _ ->
            val drawerViewModel: DrawerViewModel =
                hiltNavGraphViewModel(
                    navGraph = ChatNavGraph,
                )
            ChatScreen(
                viewModel = hiltViewModel(),
                drawerUiStateHolder = drawerViewModel,
                navigate = { route ->
                    when (route) {
                        is ChatRoute -> navController.navigateSingleTop(route)
                        else -> {
                            navController.navigate(route)
                        }
                    }
                },
            )
        }
    }
}
