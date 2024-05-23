package com.shunm.infra.database.chat.dto

import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.MessageId
import com.shunm.infra.database.chat.entity.MessageEntity
import kotlinx.datetime.Instant

object MessageDto {
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

    fun MessageEntity.toModel(): Message {
        return Message(
            id = MessageId(this.id),
            sender =
                when (this.sender) {
                    MessageEntity.SenderType.USER ->
                        Message.Sender.User(
                            com.shunm.domain.chat.model.User("User"),
                        )

                    MessageEntity.SenderType.MODEL -> Message.Sender.Model
                },
            text = this.text,
            createAt = Instant.fromEpochMilliseconds(this.createAt),
        )
    }
}
