package com.shunm.domain.chat.repository

import com.shunm.domain.common.model.ExceptionResult

interface GeminiRepository {
    suspend fun sendMessage(message: String): ExceptionResult<String>
}
