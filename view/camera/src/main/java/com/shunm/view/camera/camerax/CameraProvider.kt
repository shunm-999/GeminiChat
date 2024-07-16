package com.shunm.view.camera.camerax

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

internal class CameraProvider {
    private val mutex = Mutex()

    private var provider: ProcessCameraProvider? = null

    private suspend fun getProvider(context: Context): ProcessCameraProvider {
        return mutex.withLock {
            provider ?: withContext(Dispatchers.IO) {
                ProcessCameraProvider.getInstance(context).get()
            }.let {
                provider = it
                it
            }
        }
    }

    suspend fun hasBackCamera(context: Context): Boolean {
        val provider = getProvider(context)
        return provider.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA)
    }

    suspend fun hasFrontCamera(context: Context): Boolean {
        val provider = getProvider(context)
        return provider.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA)
    }
}

@Composable
internal fun rememberCameraProvider(): CameraProvider {
    return remember {
        CameraProvider()
    }
}
