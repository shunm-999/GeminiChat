package com.shunm.domain.chat.repository

import com.shunm.domain.chat.input_data.ThreadCreation
import com.shunm.domain.chat.model.Thread
import com.shunm.domain.chat.model.ThreadId

interface ThreadRepository {
    suspend fun getThreadList(): List<Thread>

    suspend fun getThread(threadId: ThreadId): Thread

    suspend fun createThread(thread: ThreadCreation)
}
