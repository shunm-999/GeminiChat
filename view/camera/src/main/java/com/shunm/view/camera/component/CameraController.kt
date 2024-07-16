package com.shunm.view.camera.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shunm.common_compose.theme.GeminiChatTheme

@Composable
internal fun CameraController(onTakePicture: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TakePictureButton(
            onClick = onTakePicture,
        )
    }
}

@Preview
@Composable
private fun CameraControllerPreview() {
    GeminiChatTheme {
        CameraController(onTakePicture = {})
    }
}
