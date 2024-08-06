package com.shunm.infra.chat.repository

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.net.toUri
import com.shunm.domain.chat.repository.ImageRepository
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.domain.common.model.Ok
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

internal class ImageRepositoryImpl
@Inject
constructor(
    @ApplicationContext private val context: Context,
) : ImageRepository {
    override fun createLocalImage(imageUri: Uri): ExceptionResult<Uri> {
        if (imageUri.scheme != "content") {
            return Err(IllegalArgumentException("Uri scheme must be content"))
        }

        val fileName =
            when (val result = getFileName(imageUri)) {
                is Ok -> result.value
                is Err -> return Err(result.error)
            }

        context.contentResolver.openInputStream(imageUri).use { inputStream ->
            if (inputStream == null) {
                return Err(IllegalArgumentException("Failed to open input stream"))
            }
            val outputFile = File(context.filesDir, fileName)
            outputFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            return Ok(outputFile.toUri())
        }
    }

    private fun getFileName(imageUri: Uri): ExceptionResult<String> {
        if (imageUri.scheme != "content") {
            return Err(IllegalArgumentException("Uri scheme must be content"))
        }

        val projection = arrayOf(MediaStore.Images.Media.DISPLAY_NAME)
        context.contentResolver.query(
            imageUri,
            projection,
            null,
            null,
            null,
        ).use { cursor ->
            if (cursor == null || !cursor.moveToFirst()) {
                return Err(IllegalArgumentException("Failed to get file name"))
            }
            val fileName =
                cursor.getColumnName(
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME),
                )
            return Ok(fileName)
        }
    }
}
