package com.shunm.view.camera.camerax

import android.content.Context
import android.view.Surface
import android.view.View
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.impl.ImageOutputConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.LifecycleOwner
import com.shunm.view.camera.data.LensFacing

internal class CameraProvider {
    data class CameraPreviewConfig(
        @AspectRatio.Ratio val aspectRatio: Int,
        @ImageOutputConfig.RotationValue val rotation: Int,
    )

    fun hasBackCamera(context: Context): Boolean {
        val provider = getProvider(context)
        return provider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)
    }

    fun hasFrontCamera(context: Context): Boolean {
        val provider = getProvider(context)
        return provider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
    }

    fun bindCameraToPreview(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        previewView: PreviewView,
        requireLensFacing: LensFacing = LensFacing.Back,
    ): CameraController {
        val provider = getProvider(context)

        val cameraSelector = buildCameraSelector(context, requireLensFacing)
        val preview = buildPreview(previewView)
        val imageCapture = buildImageCapture(previewView)
        val imageAnalysis = buildImageAnalysis(previewView)

        provider.unbindAll()

        provider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture,
            imageAnalysis,
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)

        return CameraController(
            imageCapture = imageCapture,
        )
    }

    private fun getProvider(context: Context): ProcessCameraProvider {
        return ProcessCameraProvider.getInstance(context).get()
    }

    private fun buildCameraSelector(
        context: Context,
        requireLensFacing: LensFacing,
    ): CameraSelector {
        val lensFacing =
            when {
                requireLensFacing is LensFacing.Front && hasFrontCamera(context) -> CameraSelector.LENS_FACING_FRONT
                requireLensFacing is LensFacing.Back && hasBackCamera(context) -> CameraSelector.LENS_FACING_BACK
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }
        return CameraSelector.Builder().requireLensFacing(lensFacing).build()
    }

    private fun buildPreview(previewView: View): Preview {
        val (screenAspectRatio, rotation) = getCameraProviderConfig(previewView)

        return Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
    }

    private fun buildImageCapture(previewView: View): ImageCapture {
        val (screenAspectRatio, rotation) = getCameraProviderConfig(previewView)
        return ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
    }

    private fun buildImageAnalysis(previewView: View): ImageAnalysis {
        val (screenAspectRatio, rotation) = getCameraProviderConfig(previewView)
        return ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
    }

    private fun getCameraProviderConfig(previewView: View): CameraPreviewConfig {
        val rotation = previewView.context.display?.rotation ?: Surface.ROTATION_0
        return CameraPreviewConfig(
            aspectRatio = AspectRatio.RATIO_4_3,
            rotation = rotation,
        )
    }
}

@Composable
internal fun rememberCameraProvider(): CameraProvider {
    return remember {
        CameraProvider()
    }
}
