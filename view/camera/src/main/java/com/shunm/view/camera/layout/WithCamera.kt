package com.shunm.view.camera.layout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.shunm.view.camera.screen.CameraScreen
import com.shunm.view.camera.util.LocalCameraNavigator
import com.shunm.view.camera.util.rememberCameraNavigator

@Composable
fun WithCamera(content: @Composable () -> Unit) {
    val cameraManager = rememberCameraNavigator()
    CompositionLocalProvider(LocalCameraNavigator provides cameraManager) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            content()
            if (cameraManager.isActive) {
                BackHandler {
                    cameraManager.deactivate()
                }
                CameraScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
