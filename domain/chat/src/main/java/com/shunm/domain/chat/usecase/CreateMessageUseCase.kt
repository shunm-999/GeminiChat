package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.input_data.MessageCreation
import com.shunm.domain.chat.model.MessageId
import com.shunm.domain.chat.repository.MessageRepository
import com.shunm.domain.common.model.ExceptionResult
import javax.inject.Inject

class CreateMessageUseCase
    @Inject
    constructor(
        private val messageRepository: MessageRepository,
    ) {
        suspend operator fun invoke(messageCreation: MessageCreation): ExceptionResult<MessageId> =
            messageRepository.createMessage(messageCreation)
    }
