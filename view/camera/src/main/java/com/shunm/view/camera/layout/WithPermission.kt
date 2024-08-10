package com.shunm.view.camera.layout

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.shunm.commonCompose.theme.GeminiChatTheme
import com.shunm.view.camera.R

@Composable
internal fun WithPermission(
    deniedContent: @Composable () -> Unit = { DeniedContent() },
    permission: String,
    grantedContent: @Composable () -> Unit,
) {
    val permissionState = rememberPermissionState(permission)

    if (permissionState.isGranted) {
        grantedContent()
    } else {
        LaunchedEffect(Unit) {
            permissionState.requestPermission()
        }
        deniedContent()
    }
}

private class PermissionState(
    isGranted: State<Boolean>,
    private val permission: String,
    private val requestPermission: (permission: String) -> Unit,
) {
    val isGranted by isGranted

    fun requestPermission() {
        requestPermission(permission)
    }
}

@Composable
private fun rememberPermissionState(permission: String): PermissionState {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val isGranted =
        remember(permission) {
            mutableStateOf(checkPermission(context, permission))
        }

    val lifecycleObserver =
        remember(permission) {
            LifecycleEventObserver { _, event ->
                isGranted.value = checkPermission(context, permission)
            }
        }
    DisposableEffect(lifecycle, lifecycleObserver, permission) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }

    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { newGranted ->
                isGranted.value = newGranted
            },
        )

    return remember {
        PermissionState(
            isGranted = isGranted,
            permission = permission,
            requestPermission = {
                launcher.launch(it)
            },
        )
    }
}

private fun checkPermission(
    context: Context,
    permission: String,
): Boolean {
    val isGranted =
        ContextCompat.checkSelfPermission(
            context,
            permission,
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    return isGranted
}

@Composable
private fun DeniedContent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Center {
            Text(text = stringResource(id = R.string.view_camera_you_should_allow_permission))
        }
    }
}

@Composable
private fun Center(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        content()
    }
}

@PreviewLightDark
@Composable
private fun DeniedContentPreview() {
    GeminiChatTheme {
        Surface {
            DeniedContent()
        }
    }
}
