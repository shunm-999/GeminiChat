package com.shunm.view.camera.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shunm.common_compose.layouts.GeminiScaffold
import com.shunm.view.camera.util.LocalCameraManager

@Composable
internal fun CameraScreen(modifier: Modifier = Modifier) {
    val cameraManager = LocalCameraManager.current
    GeminiScaffold(
        modifier = modifier,
        topBar = {
            CameraAppBar {
                cameraManager.deactivate()
            }
        },
    ) {
        Content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CameraAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = {
        },
        navigationIcon = {
            BackButton(onClick = onBackClick)
        },
    )
}

@Composable
private fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = null,
        )
    }
}

@Composable
private fun Content() {
}
