package com.shunm.commonCompose.navigation

import android.os.Parcelable
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import kotlin.reflect.KType
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

@JvmName("navigationNoArgs")
inline fun <reified NG : NavGraph, reified SD : NavigateRoute.NoArgs> GeminiChatNavGraphBuilder.navigation(
    startDestination: StartDestination<SD>,
    noinline builder: GeminiChatNavGraphBuilder.() -> Unit,
) {
    navigation<NG, SD>(
        typeMap = emptyMap(),
        startDestination = startDestination,
        builder = builder,
    )
}

@JvmName("navigationWithArgs")
inline fun <reified NG : NavGraph, reified SD : NavigateRoute.WithArgs<SDA>, reified SDA : NavigateRouteArgs> GeminiChatNavGraphBuilder.navigation(
    startDestination: StartDestination<SD>,
    noinline builder: GeminiChatNavGraphBuilder.() -> Unit,
) {
    navigation<NG, SD>(
        typeMap = mapOf(typeOf<SDA>() to parcelableNavType<SDA>()),
        startDestination = startDestination,
        builder = builder,
    )
}

inline fun <reified NG : NavGraph, reified SD : NavigateRoute> GeminiChatNavGraphBuilder.navigation(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
    startDestination: StartDestination<SD>,
    noinline builder: GeminiChatNavGraphBuilder.() -> Unit,
) {
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

@JvmName("composableNoArgs")
inline fun <reified NR : NavigateRoute.NoArgs> GeminiChatNavGraphBuilder.composable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry, NR) -> Unit,
) {
    composable<NR>(
        typeMap = emptyMap(),
        content = content,
    )
}

@JvmName("composableWithArgs")
inline fun <reified NR : NavigateRoute.WithArgs<NRA>, reified NRA : Parcelable> GeminiChatNavGraphBuilder.composable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry, NR) -> Unit,
) {
    composable(
        typeMap = mapOf(typeOf<NRA>() to parcelableNavType<NRA>()),
        content = content,
    )
}

inline fun <reified NR : NavigateRoute> GeminiChatNavGraphBuilder.composable(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry, NR) -> Unit,
) {
    provider.composable<NR>(
        typeMap = typeMap,
        content = { backStackEntry ->
            val route = backStackEntry.toRoute<NR>()
            content(backStackEntry, route)
        },
    )
}
