package com.shunm.commonCompose.navigation.ext

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.toRoute
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.navigation.NavigateRouteArgs
import com.shunm.commonCompose.navigation.parcelableNavType
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@JvmName("toNavigateRouteNoArgs")
inline fun <reified NR : NavigateRoute.NoArgs> SavedStateHandle.toNavigateRoute(): Lazy<NR> {
    return toNavigateRoute(
        typeMap = emptyMap()
    )
}

@JvmName("toNavigateRouteWithArgs")
inline fun <reified NR : NavigateRoute.WithArgs<NRA>, reified NRA : NavigateRouteArgs> SavedStateHandle.toNavigateRoute(): Lazy<NR> {
    return toNavigateRoute(
        typeMap = mapOf(typeOf<NRA>() to parcelableNavType<NRA>()),
    )
}

inline fun <reified NR : NavigateRoute> SavedStateHandle.toNavigateRoute(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>>
): Lazy<NR> {
    return lazy {
        this.toRoute<NR>(
            typeMap = typeMap,
        )
    }
}
