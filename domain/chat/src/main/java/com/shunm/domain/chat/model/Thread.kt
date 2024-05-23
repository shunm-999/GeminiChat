package com.shunm.domain.chat.model

data class ThreadId(val value: Int)

data class Thread(
    val id: ThreadId,
    val title: String,
    val messages: List<Message>,
)
