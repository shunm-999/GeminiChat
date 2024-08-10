package com.shunm.commonCompose.navigation

import kotlin.reflect.KClass

interface NavigateRoute

sealed interface NavigationRouteTemplate {
    fun toRoute(): Any

    interface WithArgs : NavigationRouteTemplate

    interface NoArgs : NavigationRouteTemplate {
        override fun toRoute(): KClass<*> {
            return this::class
        }
    }
}

interface NavGraph
