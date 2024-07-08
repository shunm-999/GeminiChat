package com.shunm.infra.database.chat.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MessageWithImages(
    @Embedded val message: MessageEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "message_id",
    )
    val images: List<ImageEntity>,
)
