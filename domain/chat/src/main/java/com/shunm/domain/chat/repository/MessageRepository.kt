package com.shunm.domain.chat.repository

import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.MessageId
import com.shunm.domain.common.model.ExceptionResult
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getMessages(threadId: Int): ExceptionResult<List<Message>>

    suspend fun createMessage(
        threadId: Int,
        message: Message,
    ) : ExceptionResult<MessageId>
}
