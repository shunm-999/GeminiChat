package com.shunm.commonCompose.navigation

import kotlin.reflect.KClass

interface NavigateRoute

interface NavGraph : NavigateRoute {
    val startDestination: StartDestination
}

inline fun startDestinationWithArgs(crossinline toRoute: () -> NavigateRoute): StartDestination.WithArgs {
    return object : StartDestination.WithArgs {
        override fun toRoute(): NavigateRoute {
            return toRoute()
        }
    }
}

inline fun <reified T : NavigateRoute> startDestinationNoArgs(): StartDestination.NoArgs<T> {
    return object : StartDestination.NoArgs<T> {
        override fun toRoute(): KClass<T> {
            return T::class
        }
    }
}

sealed interface StartDestination : NavigateRoute {
    fun toRoute(): Any

    interface WithArgs : StartDestination {
        override fun toRoute(): NavigateRoute
    }

    interface NoArgs<T : NavigateRoute> : StartDestination {
        override fun toRoute(): KClass<T>
    }
}
