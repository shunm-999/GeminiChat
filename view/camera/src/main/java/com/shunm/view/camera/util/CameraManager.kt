package com.shunm.view.camera.util

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.shunm.domain.common.coroutine.SafeSuspendContinuation
import com.shunm.domain.common.coroutine.safeSuspendCoroutine
import com.shunm.domain.common.model.Result
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed interface CameraManagerError {
    data object NoMedia : CameraManagerError
}

typealias CameraManagerResult = Result<Uri, CameraManagerError>

sealed interface CameraManager {
    suspend fun launchCamera(): CameraManagerResult

    suspend fun setResult(result: CameraManagerResult)
}

private data class CameraManagerImpl(
    private val cameraNavigator: CameraNavigator,
) : CameraManager {
    private val mutex = Mutex()

    private var continuation: SafeSuspendContinuation<Uri, CameraManagerError>? = null

    override suspend fun launchCamera(): CameraManagerResult {
        cameraNavigator.activate()
        return safeSuspendCoroutine { continuation ->
            if (mutex.tryLock()) {
                this.continuation = continuation
                mutex.unlock()
            } else {
                continuation.cancel()
            }
        }
    }

    override suspend fun setResult(result: CameraManagerResult) {
        mutex.withLock {
            continuation?.resumeWith(result)
            continuation = null
        }
        cameraNavigator.deactivate()
    }
}

@Composable
internal fun rememberCameraManager(cameraNavigator: CameraNavigator): CameraManager {
    return remember(cameraNavigator) {
        CameraManagerImpl(
            cameraNavigator = cameraNavigator,
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
