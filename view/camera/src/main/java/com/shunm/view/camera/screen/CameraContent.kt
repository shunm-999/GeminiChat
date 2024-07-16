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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.view.camera.camerax.CameraProvider
import com.shunm.view.camera.camerax.rememberCameraProvider
import com.shunm.view.camera.component.CameraController
import com.shunm.view.camera.component.CloseButton
import com.shunm.view.camera.util.LocalCameraNavigator
import com.shunm.view.camera.util.rememberCameraNavigator
import java.util.concurrent.Executors
import kotlin.math.roundToInt

@Composable
internal fun CameraContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    val cameraManager = LocalCameraNavigator.current

    val cameraProvider = rememberCameraProvider()
    val cameraExecutor =
        remember {
            Executors.newSingleThreadExecutor()
        }
    DisposableEffect(cameraExecutor) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

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

    val switchableCamera by switchableCamera(cameraProvider)

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
                update = {
                    cameraProvider.bindCameraToPreview(
                        context = context,
                        lifecycleOwner = lifecycleOwner,
                        previewView = it,
                    )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CameraController(
                switchableCamera = switchableCamera,
                onTakePicture = {
                },
                onSwitchCamera = {
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

private fun calculatePreviewSize(windowSize: IntSize): IntSize {
    val windowWidth = windowSize.width
    val windowHeight = windowSize.height

    val aspectRatio = 3f / 4f

    val newWidth: Int
    val newHeight: Int

    if (windowWidth < windowHeight) {
        newWidth = windowWidth
        newHeight = (windowWidth * (1f / aspectRatio)).roundToInt()
    } else {
        newWidth = (windowHeight * (1f / aspectRatio)).roundToInt()
        newHeight = windowHeight
    }

    return IntSize(newWidth, newHeight)
}

@Composable
private fun switchableCamera(cameraProvider: CameraProvider): State<Boolean> {
    val context = LocalContext.current
    val switchableCamera =
        remember {
            mutableStateOf(false)
        }

    LaunchedEffect(Unit) {
        switchableCamera.value =
            cameraProvider.hasBackCamera(context) && cameraProvider.hasFrontCamera(context)
    }

    return switchableCamera
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
