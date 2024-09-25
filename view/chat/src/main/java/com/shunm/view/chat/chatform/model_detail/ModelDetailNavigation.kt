package com.shunm.view.chat.chatform.model_detail

import com.shunm.commonCompose.navigation.GeminiChatNavGraphBuilder
import com.shunm.commonCompose.navigation.NavigateRoute
import com.shunm.commonCompose.navigation.composable
import com.shunm.view.chat.chatform.model_detail.screen.ModelDetailScreen
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Parcelize
@Serializable
internal data object ModelDetailRoute : NavigateRoute.NoArgs

fun GeminiChatNavGraphBuilder.modelDetailNavigation() {
    composable<ModelDetailRoute> { _, _ ->
        ModelDetailScreen(
            navigate = { route ->
                navController.navigate(route)
            }
        )
    }
}