package com.shunm.view.camera.util

import android.net.Uri
import androidx.compose.runtime.Composable
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Result

sealed interface CameraProviderError {
    data object NoMedia : CameraProviderError
}

typealias CameraProviderErrorResult = Result<Uri, CameraProviderError>

sealed interface CameraProvider {
    suspend fun launchCamera(): CameraProviderErrorResult
}

private data class CameraProviderImpl(
    private val cameraManager: CameraManager,
) : CameraProvider {
    override suspend fun launchCamera(): CameraProviderErrorResult {
        cameraManager.activate()
        return Err(CameraProviderError.NoMedia)
    }
}

@Composable
fun rememberCameraProvider(): CameraProvider {
    val cameraManager = LocalCameraManager.current
    return CameraProviderImpl(
        cameraManager = cameraManager,
    )
}
