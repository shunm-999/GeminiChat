package com.shunm.view.camera.component

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shunm.commonCompose.theme.GeminiChatTheme

@Composable
internal fun CloseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier =
        modifier
            .clip(CircleShape)
            .background(Color.Gray),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@Preview
@Composable
private fun CloseButtonPreview() {
    GeminiChatTheme {
        CloseButton(onClick = {})
    }
}
