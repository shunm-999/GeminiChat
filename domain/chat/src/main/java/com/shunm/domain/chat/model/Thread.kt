package com.shunm.domain.chat.model

data class ThreadId(val value: Int)

data class Thread(
    val id: ThreadId,
    val messages: List<Message>,
)
