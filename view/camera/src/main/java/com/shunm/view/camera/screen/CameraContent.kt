package com.shunm.view.camera.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Ok
import com.shunm.view.camera.camerax.CameraController
import com.shunm.view.camera.camerax.CameraProvider
import com.shunm.view.camera.camerax.rememberCameraProvider
import com.shunm.view.camera.component.CameraControllerTile
import com.shunm.view.camera.component.CameraPreview
import com.shunm.view.camera.component.CloseButton
import com.shunm.view.camera.data.LensFacing
import com.shunm.view.camera.util.LocalCameraNavigator
import com.shunm.view.camera.util.rememberCameraNavigator
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Composable
internal fun CameraContent(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()

    val cameraNavigator = LocalCameraNavigator.current
    val cameraProvider = rememberCameraProvider()
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }
    var cameraController: CameraController? by remember {
        mutableStateOf(null)
    }
    var currentLensFacing: LensFacing by rememberLensFacing(LensFacing.Back)

    DisposableEffect(cameraExecutor) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    val switchableCamera by switchableCamera(cameraProvider)

    Box(
        modifier =
            modifier
                .fillMaxSize()
                .background(
                    Color.Black,
                ),
    ) {
        Column {
            CameraPreview(
                update = {
                    cameraController =
                        cameraProvider.bindCameraToPreview(
                            context = context,
                            lifecycleOwner = lifecycleOwner,
                            previewView = it,
                            requireLensFacing = currentLensFacing,
                        )
                },
            )
            Spacer(modifier = Modifier.height(8.dp))
            CameraControllerTile(
                switchableCamera = switchableCamera,
                onTakePicture = {
                    coroutineScope.launch {
                        cameraController?.let { controller ->
                            when (
                                val result =
                                    controller.takePicture(
                                        context = context,
                                        executor = cameraExecutor,
                                    )
                            ) {
                                is Err -> {
                                }

                                is Ok -> {
                                }
                            }
                        }
                    }
                },
                onSwitchCamera = {
                    currentLensFacing = currentLensFacing.toInverse()
                },
            )
        }
        Box(
            modifier =
                Modifier
                    .padding(8.dp)
                    .align(Alignment.TopStart),
        ) {
            CloseButton {
                cameraNavigator.deactivate()
            }
        }
    }
}

@Composable
private fun rememberLensFacing(initial: LensFacing): MutableState<LensFacing> {
    return rememberSaveable(
        saver =
            Saver(
                save = { it.value.toValue() },
                restore = { mutableStateOf(LensFacing.from(it)) },
            ),
    ) {
        mutableStateOf(initial)
    }
}

@Composable
private fun switchableCamera(cameraProvider: CameraProvider): State<Boolean> {
    val context = LocalContext.current
    val switchableCamera =
        remember {
            mutableStateOf(false)
        }

    LaunchedEffect(Unit) {
        switchableCamera.value =
            cameraProvider.hasBackCamera(context) && cameraProvider.hasFrontCamera(context)
    }

    return switchableCamera
}

@Preview
@Composable
private fun ContentPreview() {
    GeminiChatTheme {
        val cameraManager = rememberCameraNavigator()
        CompositionLocalProvider(
            LocalCameraNavigator provides cameraManager,
        ) {
            CameraContent()
        }
    }
}
