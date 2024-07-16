package com.shunm.view.camera.screen

import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.view.camera.component.CameraController
import com.shunm.view.camera.component.CloseButton
import com.shunm.view.camera.util.LocalCameraNavigator
import com.shunm.view.camera.util.calculatePreviewSize
import com.shunm.view.camera.util.rememberCameraNavigator

@Composable
internal fun CameraContent(modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val cameraManager = LocalCameraNavigator.current

    val previewSize =
        remember(density, configuration) {
            val windowSize =
                with(density) {
                    IntSize(
                        width = configuration.screenWidthDp.dp.toPx().toInt(),
                        height = configuration.screenHeightDp.dp.toPx().toInt(),
                    )
                }
            calculatePreviewSize(windowSize)
        }

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    Color.Black,
                ),
    ) {
        Column {
            AndroidView(
                modifier = modifier,
                factory = { context ->
                    PreviewView(context).apply {
                        layoutParams =
                            android.view.ViewGroup.LayoutParams(
                                previewSize.width,
                                previewSize.height,
                            )
                    }
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CameraController(
                onTakePicture = {
                },
            )
        }
        Box(
            modifier =
                Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart),
        ) {
            CloseButton {
                cameraManager.deactivate()
            }
        }
    }
}

@Preview
@Composable
private fun ContentPreview() {
    GeminiChatTheme {
        val cameraManager = rememberCameraNavigator()
        CompositionLocalProvider(
            LocalCameraNavigator provides cameraManager,
        ) {
            CameraContent()
        }
    }
}
