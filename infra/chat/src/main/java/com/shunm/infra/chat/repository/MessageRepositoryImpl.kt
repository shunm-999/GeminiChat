package com.shunm.infra.chat.repository

import com.shunm.domain.chat.input_data.MessageCreation
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.MessageId
import com.shunm.domain.chat.repository.MessageRepository
import com.shunm.domain.common.coroutine.BasicDispatchers
import com.shunm.domain.common.coroutine.Dispatcher
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.domain.common.model.Ok
import com.shunm.infra.database.chat.dao.MessageDao
import com.shunm.infra.database.chat.dto.MessageDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MessageRepositoryImpl
    @Inject
    constructor(
        @Dispatcher(BasicDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
        private val messageDao: MessageDao,
    ) : MessageRepository {
        override suspend fun getMessages(threadId: Long): ExceptionResult<List<Message>> {
            return withContext(ioDispatcher) {
                try {
                    with(MessageDto) {
                        val messages = messageDao.selectByThreadId(threadId)
                        Ok(messages.map { it.toModel() })
                    }
                } catch (e: Exception) {
                    Err(e)
                }
            }
        }

        override fun getMessagesFlow(threadId: Long): Flow<ExceptionResult<List<Message>>> {
            return messageDao.selectByThreadIdFlow(threadId).map { entities ->
                with(MessageDto) {
                    val messages = entities.map { it.toModel() }
                    Ok(messages)
                }
            }
        }

        override suspend fun createMessage(messageCreation: MessageCreation): ExceptionResult<MessageId> {
            return withContext(ioDispatcher) {
                try {
                    with(MessageDto) {
                        val messageEntity = messageCreation.toEntity()
                        messageDao.insert(messageEntity)
                        Ok(MessageId(messageEntity.id))
                    }
                } catch (e: Exception) {
                    Err(e)
                }
            }
        }
    }
