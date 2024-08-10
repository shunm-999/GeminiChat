package com.shunm.infra.chat.repository

import android.content.Context
import android.net.Uri
import com.shunm.domain.chat.repository.GeminiRepository
import com.shunm.domain.common.ext.getBitmapOrNull
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.infra.chat.datasource.GeminiRemoteDatasource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class GeminiRepositoryImpl
@Inject
constructor(
    @ApplicationContext private val context: Context,
    private val geminiRemoteDatasource: GeminiRemoteDatasource,
) : GeminiRepository {
    override suspend fun sendMessage(
        message: String,
        imageList: List<Uri>,
    ): ExceptionResult<String> {
        val bitmapList =
            imageList.mapNotNull {
                it.getBitmapOrNull(context)
            }
        return geminiRemoteDatasource.sendMessage(
            message = message,
            imageList = bitmapList,
        )
    }
}
