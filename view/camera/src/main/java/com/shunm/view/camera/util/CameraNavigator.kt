package com.shunm.view.camera.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

internal class CameraNavigator {
    var isActive: Boolean by mutableStateOf(false)
        private set

    fun activate() {
        isActive = true
    }

    fun deactivate() {
        isActive = false
    }
}

@Composable
internal fun rememberCameraNavigator(): CameraNavigator {
    return remember {
        CameraNavigator()
    }
}
