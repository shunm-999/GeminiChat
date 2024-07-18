package com.shunm.view.camera.camerax

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.shunm.domain.common.coroutine.safeSuspendCoroutine
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.domain.common.model.Ok
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService

internal class CameraController(
    private val imageCapture: ImageCapture,
) {
    companion object {
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_TYPE = "image/jpeg"
        private const val DESTINATION_PATH = "Pictures/GeminiChat"
    }

    suspend fun takePicture(
        context: Context,
        executor: ExecutorService,
    ): ExceptionResult<Uri> {
        val name =
            SimpleDateFormat(FILENAME, Locale.US)
                .format(System.currentTimeMillis())
        val contentValues =
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, PHOTO_TYPE)
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    put(MediaStore.Images.Media.RELATIVE_PATH, DESTINATION_PATH)
                }
            }
        val outputOptions =
            ImageCapture.OutputFileOptions
                .Builder(
                    context.contentResolver,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues,
                )
                .build()

        return safeSuspendCoroutine { continuation ->
            imageCapture.takePicture(
                outputOptions,
                executor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exception: ImageCaptureException) {
                        continuation.resumeWith(Err(exception))
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        val savedUri = output.savedUri
                        if (savedUri == null) {
                            continuation.resumeWith(
                                Err(
                                    ImageCaptureException(
                                        ImageCapture.ERROR_UNKNOWN,
                                        "Image capture failed",
                                        null,
                                    ),
                                ),
                            )
                        } else {
                            continuation.resumeWith(Ok(savedUri))
                        }
                    }
                },
            )
        }
    }
}
