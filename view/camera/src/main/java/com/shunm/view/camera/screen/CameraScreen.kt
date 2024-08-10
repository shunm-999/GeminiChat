package com.shunm.view.camera.screen

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shunm.commonCompose.layouts.GeminiScaffold
import com.shunm.view.camera.layout.WithPermission

@Composable
internal fun CameraScreen(modifier: Modifier = Modifier) {
    GeminiScaffold(
        modifier = modifier,
    ) {
        WithPermission(permission = Manifest.permission.CAMERA) {
            CameraContent()
        }
    }
}
