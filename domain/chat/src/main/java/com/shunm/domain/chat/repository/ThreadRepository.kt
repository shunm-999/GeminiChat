package com.shunm.domain.chat.repository

import com.shunm.domain.chat.inputData.ThreadCreation
import com.shunm.domain.chat.model.ThreadDetail
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.model.ThreadSummary
import com.shunm.domain.common.model.ExceptionResult
import kotlinx.coroutines.flow.Flow

interface ThreadRepository {
    suspend fun getThreadList(): ExceptionResult<List<ThreadSummary>>

    fun getThreadListFlow(): Flow<ExceptionResult<List<ThreadSummary>>>

    suspend fun getThread(threadId: ThreadId): ExceptionResult<ThreadSummary>

    fun getThreadFlow(threadId: ThreadId): Flow<ExceptionResult<ThreadSummary>>

    suspend fun getThreadDetail(threadId: ThreadId): ExceptionResult<ThreadDetail>

    fun getThreadDetailFlow(threadId: ThreadId): Flow<ExceptionResult<ThreadDetail>>

    suspend fun createThread(thread: ThreadCreation): ExceptionResult<ThreadId>
}
