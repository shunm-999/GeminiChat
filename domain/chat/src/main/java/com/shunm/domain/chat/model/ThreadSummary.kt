package com.shunm.domain.chat.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class ThreadSummary(
    val id: ThreadId,
    val title: String,
    val createAt: Instant,
) {
    constructor(
        title: String,
    ) : this(ThreadId(0), title, Clock.System.now())

    constructor(
        title: String,
        createAt: Instant,
    ) : this(ThreadId(0), title, createAt)
}
