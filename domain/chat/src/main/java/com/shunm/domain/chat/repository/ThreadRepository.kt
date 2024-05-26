package com.shunm.domain.chat.repository

import com.shunm.domain.chat.input_data.ThreadCreation
import com.shunm.domain.chat.model.Thread
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.common.model.ExceptionResult
import kotlinx.coroutines.flow.Flow

interface ThreadRepository {
    suspend fun getThreadList(): ExceptionResult<List<Thread>>

    suspend fun getThread(threadId: ThreadId): ExceptionResult<Thread>

    suspend fun getThreadFlow(threadId: ThreadId): Flow<ExceptionResult<Thread>>

    suspend fun createThread(thread: ThreadCreation): ExceptionResult<ThreadId>
}
