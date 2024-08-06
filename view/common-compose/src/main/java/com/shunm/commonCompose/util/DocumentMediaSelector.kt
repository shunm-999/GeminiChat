package com.shunm.commonCompose.util

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
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

sealed interface DocumentPhotoSelectorError {
    data object NoMedia : DocumentPhotoSelectorError
}

typealias DocumentPhotoSelectorResult = Result<List<Uri>, DocumentPhotoSelectorError>

sealed interface DocumentMediaSelector {
    suspend fun selectMedia(): DocumentPhotoSelectorResult
}

private class DocumentMediaSelectorImpl : DocumentMediaSelector {
    var launcher: ManagedActivityResultLauncher<String, List<Uri>>? = null

    private val mutex = Mutex()
    private var safeSuspendContinuation: SafeSuspendContinuation<List<Uri>, DocumentPhotoSelectorError>? =
        null

    override suspend fun selectMedia(): DocumentPhotoSelectorResult =
        mutex.withLock {
            safeSuspendCoroutine { continuation ->
                safeSuspendContinuation = continuation
                launcher?.launch("*/*")
            }
        }

    fun resume(result: DocumentPhotoSelectorResult) {
        safeSuspendContinuation?.resumeWith(result)
    }
}

@Composable
fun rememberDocumentMediaSelector(): DocumentMediaSelector {
    val documentPhotoSelector =
        remember {
            DocumentMediaSelectorImpl()
        }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.GetMultipleContents(),
        ) { uris ->
            if (uris.isNotEmpty()) {
                documentPhotoSelector.resume(Ok(uris))
            } else {
                documentPhotoSelector.resume(Err(DocumentPhotoSelectorError.NoMedia))
            }
        }
    LaunchedEffect(launcher) {
        documentPhotoSelector.launcher = launcher
    }
    return documentPhotoSelector
}
