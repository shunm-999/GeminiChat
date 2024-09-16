package com.shunm.commonCompose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute

sealed interface GeminiChatNavGraphBuilder {
    val provider: NavGraphBuilder
    val navController: NavHostController
}

fun GeminiChatNavGraphBuilder(
    builder: NavGraphBuilder,
    navController: NavHostController,
): GeminiChatNavGraphBuilder {
    return GeminiChatNavGraphBuilderImpl(
        innerBuilder = builder,
        navController = navController,
    )
}

private class GeminiChatNavGraphBuilderImpl(
    innerBuilder: NavGraphBuilder,
    override val navController: NavHostController,
) : GeminiChatNavGraphBuilder {
    override val provider: NavGraphBuilder = innerBuilder
}

inline fun <reified T : NavGraph> GeminiChatNavGraphBuilder.navigation(
    startDestination: StartDestination,
    noinline builder: GeminiChatNavGraphBuilder.() -> Unit,
) {
    when (startDestination) {
        is StartDestination.NoArgs<*> -> {
            provider.navigation<T>(
                startDestination = startDestination.toRoute(),
                builder = {
                    GeminiChatNavGraphBuilder(
                        builder = this,
                        navController = navController,
                    ).builder()
                },
            )
        }

        is StartDestination.WithArgs -> {
            provider.navigation<T>(
                startDestination = startDestination.toRoute(),
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

inline fun <reified T : NavigateRoute> GeminiChatNavGraphBuilder.composable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry, T) -> Unit,
) {
    provider.composable<T>(
        content = { backStackEntry ->
            val route = backStackEntry.toRoute<T>()
            content(backStackEntry, route)
        },
    )
}
