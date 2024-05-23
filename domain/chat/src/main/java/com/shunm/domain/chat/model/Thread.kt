package com.shunm.domain.chat.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class ThreadId(val value: Long)

data class Thread(
    val id: ThreadId,
    val title: String,
    val messages: List<Message>,
    val createAt: Instant,
) {
    constructor(
        title: String,
        messages: List<Message>,
    ) : this(ThreadId(0), title, messages, Clock.System.now())

    constructor(
        title: String,
        messages: List<Message>,
        createAt: Instant,
    ) : this(ThreadId(0), title, messages, createAt)
}
