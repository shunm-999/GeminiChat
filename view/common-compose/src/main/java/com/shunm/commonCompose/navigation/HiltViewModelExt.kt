package com.shunm.commonCompose.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel

@Composable
inline fun <reified VM : ViewModel> GeminiChatNavGraphBuilder.hiltNavGraphViewModel(
    navGraph: NavGraph,
): VM {
    val parentEntry = navController.getBackStackEntry(navGraph)
    return hiltViewModel<VM>(parentEntry)
}
