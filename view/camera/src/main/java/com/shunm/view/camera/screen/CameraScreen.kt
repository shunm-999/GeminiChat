package com.shunm.view.camera.screen

import android.Manifest
import androidx.camera.view.PreviewView
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.shunm.common_compose.layouts.GeminiScaffold
import com.shunm.view.camera.layout.WithPermission
import com.shunm.view.camera.util.LocalCameraNavigator

@Composable
internal fun CameraScreen(modifier: Modifier = Modifier) {
    val cameraManager = LocalCameraNavigator.current
    GeminiScaffold(
        modifier = modifier,
        topBar = {
            CameraAppBar {
                cameraManager.deactivate()
            }
        },
    ) {
        WithPermission(permission = Manifest.permission.CAMERA) {
            Content()
        }
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
private fun Content(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            PreviewView(context).apply {
                layoutParams =
                    android.view.ViewGroup.LayoutParams(
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    )
            }
        },
    )
}
