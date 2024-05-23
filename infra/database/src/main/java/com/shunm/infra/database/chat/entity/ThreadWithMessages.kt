package com.shunm.infra.database.chat.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ThreadWithMessages(
    @Embedded val thread: ThreadEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "thread_id",
    )
    val messages: List<MessageEntity>,
)
