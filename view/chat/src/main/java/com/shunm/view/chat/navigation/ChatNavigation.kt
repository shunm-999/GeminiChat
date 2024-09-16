package com.shunm.view.chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import com.shunm.commonCompose.navigation.GeminiChatNavGraphBuilder
import com.shunm.commonCompose.navigation.NavGraph
import com.shunm.commonCompose.navigation.NavigateRoute
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
        get() = startDestinationWithArgs { ChatRoute(threadId = ThreadId.undefined) }
}

@Parcelize
@Serializable
data class ChatRoute internal constructor(
    val threadId: ThreadId,
) : NavigateRoute {
    companion object {
        fun newChat(): ChatRoute = ChatRoute(threadId = ThreadId.undefined)
    }
}

fun GeminiChatNavGraphBuilder.chatNavGraph() {
    navigation<ChatNavGraph, ChatRoute>(
        startDestination = ChatNavGraph.startDestination,
    ) {
        composable<ChatRoute> { _, _ ->
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
                    }
                },
            )
        }
    }
}
