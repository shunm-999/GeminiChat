package com.shunm.view.camera.util

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Result

sealed interface CameraProviderError {
    data object NoMedia : CameraProviderError
}

typealias CameraProviderErrorResult = Result<Uri, CameraProviderError>

sealed interface CameraManager {
    suspend fun launchCamera(): CameraProviderErrorResult
}

private data class CameraManagerImpl(
    private val cameraNavigator: CameraNavigator,
) : CameraManager {
    override suspend fun launchCamera(): CameraProviderErrorResult {
        cameraNavigator.activate()
        return Err(CameraProviderError.NoMedia)
    }
}

@Composable
fun rememberCameraManager(): CameraManager {
    val cameraManager = LocalCameraNavigator.current
    return remember(cameraManager) {
        CameraManagerImpl(
            cameraNavigator = cameraManager,
        )
    }
}

// DisplayManager
// DisplayManager.DisplayListener register / unregister
// CameraExecutor
// WindowManager / getCurrentWindowMetrics().bounds

// updateCameraUi
// - takePicture
// - cameraSwitch
// setUpCamera
// - cameraProvider
// - updateCameraSwitchButton
// - bindCameraUseCases
// updateCameraSwitchButton
// bindCameraUseCases
// - cameraProvider
// - cameraSelector
// - Preview
// - ImageCapture
// - ImageAnalysis
// - CameraProvider.unbindAll
// - CameraProvider.bindToLifecycle
// - Preview.setSurfaceProvider
