package com.shunm.view.camera.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shunm.common_compose.theme.GeminiChatTheme

@Composable
internal fun TakePictureButton(onClick: () -> Unit) {
    OuterCircle(
        onClick = onClick,
    ) {
        InnerCircle()
    }
}

@Composable
private fun OuterCircle(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    inner: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .size(56.dp)
                .clickable { onClick() }
                .clip(CircleShape)
                .background(Color.Black)
                .border(4.dp, SolidColor(Color.White), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        inner()
    }
}

@Composable
private fun InnerCircle(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White),
    ) {
    }
}

@Preview
@Composable
private fun TakePictureButtonPreview() {
    GeminiChatTheme {
        TakePictureButton(onClick = {})
    }
}
