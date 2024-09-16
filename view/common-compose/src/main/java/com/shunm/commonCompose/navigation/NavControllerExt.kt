package com.shunm.commonCompose.navigation

import androidx.navigation.NavController

inline fun <reified T : NavigateRoute> NavController.navigateSingleTop(route: T) {
    navigate(route) {
        popUpTo<T> {
            inclusive = true
        }
    }
}
