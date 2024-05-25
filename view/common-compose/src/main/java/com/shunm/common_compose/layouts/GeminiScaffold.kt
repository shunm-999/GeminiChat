package com.shunm.common_compose.layouts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GeminiScaffold(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Scaffold(
        modifier = modifier,
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding),
        ) {
            content()
        }
    }
}
