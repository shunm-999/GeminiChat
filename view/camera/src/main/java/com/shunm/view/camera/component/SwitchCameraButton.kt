package com.shunm.view.camera.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shunm.commonCompose.theme.GeminiChatTheme

@Composable
internal fun SwitchCameraButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    IconButton(
        modifier =
        modifier
            .size(56.dp)
            .border(2.dp, SolidColor(Color.White), CircleShape),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.Default.Cameraswitch,
            contentDescription = null,
            tint = Color.White,
        )
    }
}

@Preview
@Composable
private fun SwitchCameraButtonPreview() {
    GeminiChatTheme {
        SwitchCameraButton(onClick = {})
    }
}
