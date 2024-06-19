package com.shunm.domain.chat.repository

import android.net.Uri
import com.shunm.domain.common.model.ExceptionResult

interface GeminiRepository {
    suspend fun sendMessage(
        message: String,
        imageList: List<Uri>,
    ): ExceptionResult<String>
}
