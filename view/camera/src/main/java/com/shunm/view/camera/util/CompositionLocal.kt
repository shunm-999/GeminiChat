package com.shunm.view.camera.util

import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalCameraNavigator =
    staticCompositionLocalOf<CameraNavigator> {
        error("No CameraManager provided")
    }
