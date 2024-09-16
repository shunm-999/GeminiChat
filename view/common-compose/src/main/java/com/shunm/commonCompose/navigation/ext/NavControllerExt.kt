package com.shunm.commonCompose.navigation.ext

import androidx.navigation.NavController
import com.shunm.commonCompose.navigation.NavigateRoute

inline fun <reified T : NavigateRoute> NavController.navigateSingleTop(route: T) {
    navigate(route) {
        popUpTo<T> {
            inclusive = true
        }
    }
}
