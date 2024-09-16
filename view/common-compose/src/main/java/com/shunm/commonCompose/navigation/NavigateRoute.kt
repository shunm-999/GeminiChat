package com.shunm.commonCompose.navigation

import android.os.Parcelable
import kotlin.reflect.KClass

interface NavigateRoute : Parcelable

interface NavGraph : NavigateRoute {
    val startDestination: StartDestination<*>
}

inline fun <T : NavigateRoute> startDestinationWithArgs(crossinline toRoute: () -> T): StartDestination.WithArgs<T> {
    return object : StartDestination.WithArgs<T> {
        override fun toRoute(): T {
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

sealed interface StartDestination<T : NavigateRoute> {
    fun toRoute(): Any

    interface WithArgs<T : NavigateRoute> : StartDestination<T> {
        override fun toRoute(): T
    }

    interface NoArgs<T : NavigateRoute> : StartDestination<T> {
        override fun toRoute(): KClass<T>
    }
}
