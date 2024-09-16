package com.shunm.commonCompose.navigation

import kotlin.reflect.KClass

interface NavigateRoute

interface NavGraph {
    val startDestination: StartDestination

    sealed interface StartDestination : NavigateRoute {
        fun toRoute(): Any

        interface WithArgs : StartDestination {
            override fun toRoute(): NavigateRoute
        }

        interface NoArgs<T : NavigateRoute> : StartDestination {
            override fun toRoute(): KClass<T>
        }
    }
}

inline fun startDestinationWithArgs(crossinline toRoute: () -> NavigateRoute): NavGraph.StartDestination.WithArgs {
    return object : NavGraph.StartDestination.WithArgs {
        override fun toRoute(): NavigateRoute {
            return toRoute()
        }
    }
}

inline fun <reified T : NavigateRoute> startDestinationNoArgs(): NavGraph.StartDestination.NoArgs<T> {
    return object : NavGraph.StartDestination.NoArgs<T> {
        override fun toRoute(): KClass<T> {
            return T::class
        }
    }
}
