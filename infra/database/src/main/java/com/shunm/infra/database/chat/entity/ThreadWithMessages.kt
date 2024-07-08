package com.shunm.infra.database.chat.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ThreadWithMessages(
    @Embedded val thread: ThreadEntity,
    @Relation(
        entity = MessageEntity::class,
        parentColumn = "id",
        entityColumn = "thread_id",
    )
    val messageWithImages: List<MessageWithImages>,
)
