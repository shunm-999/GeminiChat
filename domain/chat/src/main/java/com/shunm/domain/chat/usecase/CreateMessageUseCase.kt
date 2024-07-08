package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.input_data.MessageCreation
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.repository.GeminiRepository
import com.shunm.domain.chat.repository.ImageRepository
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
        private val imageRepository: ImageRepository,
    ) {
        suspend operator fun invoke(messageCreation: MessageCreation): ExceptionResult<Unit> {
            val localImageList =
                messageCreation.imageList.mapNotNull {
                    when (val result = imageRepository.createLocalImage(it)) {
                        is Ok -> result.value
                        is Err -> null
                    }
                }
            val messageCreationWithLocalImages = messageCreation.copy(imageList = localImageList)

            when (val result = messageRepository.createMessage(messageCreationWithLocalImages)) {
                is Ok -> result.value
                is Err -> return Err(result.error)
            }

            val modelResponse =
                when (
                    val result =
                        geminiRepository.sendMessage(
                            message = messageCreationWithLocalImages.text,
                            imageList = messageCreationWithLocalImages.imageList,
                        )
                ) {
                    is Ok -> result.value
                    is Err -> return Err(result.error)
                }
            val modelMessageCreation =
                MessageCreation(
                    threadId = messageCreationWithLocalImages.threadId,
                    sender = Message.Sender.Model,
                    text = modelResponse,
                    imageList = emptyList(),
                    createAt = Clock.System.now(),
                )
            return when (val result = messageRepository.createMessage(modelMessageCreation)) {
                is Ok -> Ok(Unit)
                is Err -> Err(result.error)
            }
        }
    }
