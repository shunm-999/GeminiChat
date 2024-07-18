package com.shunm.view.camera.component

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.math.roundToInt

@Composable
internal fun CameraPreview(
    modifier: Modifier = Modifier,
    update: (PreviewView) -> Unit,
) {
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

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
        update = update,
    )
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
