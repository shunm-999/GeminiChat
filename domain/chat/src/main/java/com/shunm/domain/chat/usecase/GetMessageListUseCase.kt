package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.repository.MessageRepository
import com.shunm.domain.common.Suspend
import com.shunm.domain.common.model.ExceptionResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageListUseCase
@Inject
constructor(
    private val messageRepository: MessageRepository,
) {
    operator fun invoke(threadId: Long): Flow<ExceptionResult<List<Message>>> = messageRepository.getMessagesFlow(threadId)

    suspend operator fun invoke(
        @Suppress("UNUSED_PARAMETER")
        suspended: Suspend,
        threadId: Long,
    ): ExceptionResult<List<Message>> = messageRepository.getMessages(threadId)
}
