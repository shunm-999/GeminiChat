package com.shunm.view.camera.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.shunm.view.camera.screen.CameraScreen
import com.shunm.view.camera.util.LocalCameraManager
import com.shunm.view.camera.util.rememberCameraManager

@Composable
fun WithCamera(content: @Composable () -> Unit) {
    val cameraManager = rememberCameraManager()
    CompositionLocalProvider(LocalCameraManager provides cameraManager) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            content()
            if (cameraManager.isActive) {
                CameraScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
