package com.shunm.view.camera.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

internal class CameraNavigator(initialActive: Boolean) {
    var isActive: Boolean by mutableStateOf(initialActive)
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
    return rememberSaveable(
        saver =
        Saver(
            save = { it.isActive },
            restore = {
                CameraNavigator(it)
            },
        ),
    ) {
        CameraNavigator(false)
    }
}
