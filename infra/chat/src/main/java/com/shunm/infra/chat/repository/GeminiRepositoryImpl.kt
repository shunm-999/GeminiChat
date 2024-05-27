package com.shunm.infra.chat.repository

import com.shunm.domain.chat.repository.GeminiRepository
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.infra.chat.datasource.GeminiRemoteDatasource
import javax.inject.Inject

internal class GeminiRepositoryImpl
    @Inject
    constructor(
        private val geminiRemoteDatasource: GeminiRemoteDatasource,
    ) : GeminiRepository {
        override suspend fun sendMessage(message: String): ExceptionResult<String> {
            return geminiRemoteDatasource.sendMessage(message)
        }
    }
