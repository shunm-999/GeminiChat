package com.shunm.domain.chat.repository

import android.net.Uri
import com.shunm.domain.common.model.ExceptionResult

interface ImageRepository {
    fun createLocalImage(imageUri: Uri): ExceptionResult<Uri>
}
