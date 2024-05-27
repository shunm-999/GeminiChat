package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.input_data.MessageCreation
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.repository.GeminiRepository
import com.shunm.domain.chat.repository.MessageRepository
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.domain.common.model.Ok
import kotlinx.datetime.Clock
import javax.inject.Inject

class CreateMessageUseCase
    @Inject
    constructor(
        private val messageRepository: MessageRepository,
        private val geminiRepository: GeminiRepository,
    ) {
        suspend operator fun invoke(messageCreation: MessageCreation): ExceptionResult<Unit> {
            when (val result = messageRepository.createMessage(messageCreation)) {
                is Ok -> result.value
                is Err -> return Err(result.error)
            }

            val modelResponse =
                when (val result = geminiRepository.sendMessage(messageCreation.text)) {
                    is Ok -> result.value
                    is Err -> return Err(result.error)
                }
            val modelMessageCreation =
                MessageCreation(
                    threadId = messageCreation.threadId,
                    sender = Message.Sender.Model,
                    text = modelResponse,
                    createAt = Clock.System.now(),
                )
            return when (val result = messageRepository.createMessage(modelMessageCreation)) {
                is Ok -> Ok(Unit)
                is Err -> Err(result.error)
            }
        }
    }
