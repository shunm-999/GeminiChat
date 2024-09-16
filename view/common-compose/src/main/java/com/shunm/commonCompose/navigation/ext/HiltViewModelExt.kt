package com.shunm.commonCompose.navigation.ext

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.shunm.commonCompose.navigation.GeminiChatNavGraphBuilder
import com.shunm.commonCompose.navigation.NavGraph

@Composable
inline fun <reified VM : ViewModel> GeminiChatNavGraphBuilder.hiltNavGraphViewModel(
    navGraph: NavGraph,
): VM {
    val parentEntry = navController.getBackStackEntry(navGraph)
    return hiltViewModel<VM>(parentEntry)
}
