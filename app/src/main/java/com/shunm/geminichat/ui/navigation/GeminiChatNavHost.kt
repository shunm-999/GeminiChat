package com.shunm.geminichat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.shunm.view.chat.navigation.ChatRoute
import com.shunm.view.chat.navigation.chatNavGraph
import kotlin.reflect.KClass

@Composable
internal fun GeminiChatNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    GeminiChatNavHost(
        navController = navController,
        startDestination = ChatRoute::class,
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
    startDestination: KClass<*>,
    builder: NavGraphBuilder.() -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        builder = builder,
    )
}
