package com.shunm.common_compose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> hiltNavGraphViewModel(
    navController: NavController,
    navGraph: NavGraph,
): VM {
    val parentEntry = navController.getBackStackEntry(navGraph)
    return hiltViewModel<VM>(parentEntry)
}
