package com.shunm.view.camera.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shunm.common_compose.theme.GeminiChatTheme

@Composable
internal fun CameraController(
    switchableCamera: Boolean,
    onTakePicture: () -> Unit,
    onSwitchCamera: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.weight(1f)) {
        }
        Padding(16.dp) {
            TakePictureButton(
                onClick = onTakePicture,
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            if (switchableCamera) {
                Padding(16.dp) {
                    SwitchCameraButton(
                        onClick = onSwitchCamera,
                    )
                }
            }
        }
    }
}

@Composable
private fun RowScope.Padding(
    horizontal: Dp,
    content: @Composable () -> Unit,
) {
    Spacer(modifier = Modifier.width(horizontal))
    content()
    Spacer(modifier = Modifier.width(horizontal))
}

@Preview
@Composable
private fun CameraControllerPreview() {
    GeminiChatTheme {
        CameraController(
            switchableCamera = true,
            onTakePicture = {},
            onSwitchCamera = {},
        )
    }
}
