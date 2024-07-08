package com.shunm.infra.database.chat.dto

import android.net.Uri
import com.shunm.domain.chat.input_data.MessageCreation
import com.shunm.domain.chat.model.Image
import com.shunm.domain.chat.model.ImageId
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.MessageId
import com.shunm.infra.database.chat.entity.ImageEntity
import com.shunm.infra.database.chat.entity.MessageEntity
import com.shunm.infra.database.chat.entity.MessageWithImages
import kotlinx.datetime.Instant

object MessageDto {
    fun MessageCreation.toEntity(): MessageEntity {
        return MessageEntity(
            id = 0,
            threadId = threadId.value,
            sender =
                when (this.sender) {
                    is Message.Sender.User -> MessageEntity.SenderType.USER
                    is Message.Sender.Model -> MessageEntity.SenderType.MODEL
                },
            text = this.text,
            createAt = this.createAt.toEpochMilliseconds(),
        )
    }

    fun Message.toEntity(threadId: Long): MessageEntity {
        return MessageEntity(
            id = this.id.value,
            threadId = threadId,
            sender =
                when (this.sender) {
                    is Message.Sender.User -> MessageEntity.SenderType.USER
                    is Message.Sender.Model -> MessageEntity.SenderType.MODEL
                },
            text = this.text,
            createAt = this.createAt.toEpochMilliseconds(),
        )
    }

    fun MessageWithImages.toModel(): Message {
        val message = this.message
        val imageList = this.images.map { it.toModel() }
        return Message(
            id = MessageId(message.id),
            sender =
                when (message.sender) {
                    MessageEntity.SenderType.USER ->
                        Message.Sender.User(
                            com.shunm.domain.chat.model.User("User"),
                        )

                    MessageEntity.SenderType.MODEL -> Message.Sender.Model
                },
            text = message.text,
            imageList = imageList,
            createAt = Instant.fromEpochMilliseconds(message.createAt),
        )
    }

    fun ImageEntity.toModel(): Image {
        return Image(
            id = ImageId(this.id),
            url = Uri.parse(this.url),
        )
    }
}
