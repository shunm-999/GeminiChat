package com.shunm.view.camera.util

import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalCameraManager =
    staticCompositionLocalOf<CameraManager> {
        error("No CameraManager provided")
    }
