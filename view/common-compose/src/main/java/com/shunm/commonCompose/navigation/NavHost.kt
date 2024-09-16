package com.shunm.commonCompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun GeminiChatNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: NavGraph.StartDestination,
    builder: GeminiChatNavGraphBuilder.() -> Unit,
) {
    when (startDestination) {
        is NavGraph.StartDestination.NoArgs<*> -> {
            NavHost(
                navController = navController,
                startDestination = startDestination.toRoute(),
                modifier = modifier,
                builder = {
                    GeminiChatNavGraphBuilder(
                        builder = this,
                        navController = navController,
                    ).builder()
                },
            )
        }

        is NavGraph.StartDestination.WithArgs -> {
            NavHost(
                navController = navController,
                startDestination = startDestination.toRoute(),
                modifier = modifier,
                builder = {
                    GeminiChatNavGraphBuilder(
                        builder = this,
                        navController = navController,
                    ).builder()
                },
            )
        }
    }
}
