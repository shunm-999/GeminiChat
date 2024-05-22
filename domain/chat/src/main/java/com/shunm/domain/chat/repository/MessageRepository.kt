package com.shunm.domain.chat.repository

import com.shunm.domain.chat.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun getMessages(threadId: Int): List<Message>

    fun getMessageFlow(threadId: Int): Flow<List<Message>>

    suspend fun createMessage(
        threadId: Int,
        message: Message,
    )
}
