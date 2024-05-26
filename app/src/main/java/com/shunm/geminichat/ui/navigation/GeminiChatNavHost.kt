package com.shunm.geminichat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shunm.common_compose.navigation.NavigateTemplate
import com.shunm.view.chat.navigation.ChatRoute
import com.shunm.view.chat.navigation.chatNavGraph

@Composable
internal fun GeminiChatNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    GeminiChatNavHost(
        navController = navController,
        startDestination = ChatRoute,
        modifier = modifier,
    ) {
        chatNavGraph(
            navController = navController,
        )
    }
}

@Composable
internal fun GeminiChatNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: NavigateTemplate,
    builder: NavGraphBuilder.() -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.toRoute(),
        modifier = modifier,
        builder = builder,
    )
}
