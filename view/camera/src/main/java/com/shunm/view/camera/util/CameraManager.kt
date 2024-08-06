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
    private val activateMutex = Mutex()
    private val deactivateMutex = Mutex()

    private var continuation: SafeSuspendContinuation<Uri, CameraManagerError>? = null

    override suspend fun launchCamera(): CameraManagerResult {
        return activateMutex.withLock {
            cameraNavigator.activate()
            safeSuspendCoroutine { continuation ->
                this.continuation = continuation
            }
        }
    }

    override suspend fun setResult(result: CameraManagerResult) {
        deactivateMutex.withLock {
            continuation?.resumeWith(result)
            continuation = null
            cameraNavigator.deactivate()
        }
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
