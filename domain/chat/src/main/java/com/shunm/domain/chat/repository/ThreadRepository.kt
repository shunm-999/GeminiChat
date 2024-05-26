package com.shunm.domain.chat.repository

import com.shunm.domain.chat.input_data.ThreadCreation
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.model.ThreadSummary
import com.shunm.domain.common.model.ExceptionResult
import kotlinx.coroutines.flow.Flow

interface ThreadRepository {
    suspend fun getThreadList(): ExceptionResult<List<ThreadSummary>>

    suspend fun getThread(threadId: ThreadId): ExceptionResult<ThreadSummary>

    fun getThreadFlow(threadId: ThreadId): Flow<ExceptionResult<ThreadSummary>>

    suspend fun createThread(thread: ThreadCreation): ExceptionResult<ThreadId>
}
