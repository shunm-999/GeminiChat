package com.shunm.view.camera.layout

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.shunm.view.camera.screen.CameraScreen
import com.shunm.view.camera.util.LocalCameraManager
import com.shunm.view.camera.util.rememberCameraManager
import com.shunm.view.camera.util.rememberCameraNavigator

@Composable
fun WithCamera(content: @Composable () -> Unit) {
    val cameraNavigator = rememberCameraNavigator()
    val cameraManager = rememberCameraManager(cameraNavigator)
    CompositionLocalProvider(LocalCameraManager provides cameraManager) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            content()
            if (cameraNavigator.isActive) {
                BackHandler {
                    cameraNavigator.deactivate()
                }
                CameraScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }
}
