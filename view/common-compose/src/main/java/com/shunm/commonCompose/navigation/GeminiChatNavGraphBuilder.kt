package com.shunm.commonCompose.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlin.reflect.typeOf

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

inline fun <reified NG : NavGraph, reified SD : NavigateRoute> GeminiChatNavGraphBuilder.navigation(
    startDestination: StartDestination<SD>,
    noinline builder: GeminiChatNavGraphBuilder.() -> Unit,
) {
    val typeMap = mapOf(
        typeOf<NG>() to parcelableNavType<NG>(),
        typeOf<SD>() to parcelableNavType<SD>(),
    )
    when (startDestination) {
        is StartDestination.NoArgs<*> -> {
            provider.navigation<NG>(
                startDestination = startDestination.toRoute(),
                typeMap = typeMap,
                builder = {
                    GeminiChatNavGraphBuilder(
                        builder = this,
                        navController = navController,
                    ).builder()
                },
            )
        }

        is StartDestination.WithArgs -> {
            provider.navigation<NG>(
                startDestination = startDestination.toRoute(),
                typeMap = typeMap,
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
        typeMap = mapOf(typeOf<T>() to parcelableNavType<T>()),
        content = { backStackEntry ->
            val route = backStackEntry.toRoute<T>()
            content(backStackEntry, route)
        },
    )
}
