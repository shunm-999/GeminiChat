package com.shunm.commonCompose.util

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.shunm.domain.common.coroutine.SafeSuspendContinuation
import com.shunm.domain.common.coroutine.safeSuspendCoroutine
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.Ok
import com.shunm.domain.common.model.Result
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

sealed interface PhotoPickerError {
    data object NoMedia : PhotoPickerError

    data object Unknown : PhotoPickerError
}

typealias PhotoPickerResult = Result<List<Uri>, PhotoPickerError>

sealed interface PhotoPicker {
    suspend fun pickMedia(): PhotoPickerResult
}

private class PhotoPickerImpl : PhotoPicker {
    var launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, List<Uri>>? = null

    private val mutex = Mutex()
    private var safeSuspendContinuation: SafeSuspendContinuation<List<Uri>, PhotoPickerError>? =
        null

    override suspend fun pickMedia(): PhotoPickerResult =
        mutex.withLock {
            safeSuspendCoroutine { continuation ->
                safeSuspendContinuation = continuation
                launcher?.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                )
            }
        }

    fun resume(result: PhotoPickerResult) {
        safeSuspendContinuation?.resumeWith(result)
    }
}

@Composable
fun rememberPhotoPicker(): PhotoPicker {
    val photoPicker =
        remember {
            PhotoPickerImpl()
        }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) { uris ->
            photoPicker.resume(
                if (uris.isNotEmpty()) {
                    Ok(uris)
                } else {
                    Err(PhotoPickerError.NoMedia)
                },
            )
        }
    LaunchedEffect(launcher) {
        photoPicker.launcher = launcher
    }
    return photoPicker
}
