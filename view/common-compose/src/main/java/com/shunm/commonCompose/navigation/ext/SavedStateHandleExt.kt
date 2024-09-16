package com.shunm.commonCompose.navigation.ext

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.navigation.parcelableNavType
import kotlin.reflect.typeOf

inline fun <reified T : NavigateRoute> SavedStateHandle.toNavigateRoute(): Lazy<T> {
    return lazy {
        this.toRoute<T>(
            typeMap = mapOf(typeOf<T>() to parcelableNavType<T>()),
        )
    }
}
