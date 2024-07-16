package com.shunm.view.camera.util

import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

internal fun calculatePreviewSize(windowSize: IntSize): IntSize {
    val windowWidth = windowSize.width
    val windowHeight = windowSize.height

    val aspectRatio = 2f / 3f

    val newWidth: Int
    val newHeight: Int

    if (windowWidth < windowHeight) {
        newWidth = windowWidth
        newHeight = (windowHeight * aspectRatio).roundToInt()
    } else {
        newWidth = (windowWidth * aspectRatio).roundToInt()
        newHeight = windowHeight
    }

    return IntSize(newWidth, newHeight)
}
