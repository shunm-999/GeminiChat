package com.shunm.commonCompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun GeminiChatNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: StartDestination<*>,
    builder: GeminiChatNavGraphBuilder.() -> Unit,
) {
    when (startDestination) {
        is StartDestination.NoArgs<*> -> {
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

        is StartDestination.WithArgs<*> -> {
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
