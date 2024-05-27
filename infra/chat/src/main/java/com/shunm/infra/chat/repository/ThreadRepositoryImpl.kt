package com.shunm.infra.chat.repository

import com.shunm.domain.chat.input_data.ThreadCreation
import com.shunm.domain.chat.model.ThreadDetail
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.model.ThreadSummary
import com.shunm.domain.chat.repository.ThreadRepository
import com.shunm.domain.common.coroutine.BasicDispatchers
import com.shunm.domain.common.coroutine.Dispatcher
import com.shunm.domain.common.model.Err
import com.shunm.domain.common.model.ExceptionResult
import com.shunm.domain.common.model.Ok
import com.shunm.infra.database.chat.dao.ThreadDao
import com.shunm.infra.database.chat.dto.ThreadDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ThreadRepositoryImpl
    @Inject
    constructor(
        @Dispatcher(BasicDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
        private val threadDao: ThreadDao,
    ) : ThreadRepository {
        override suspend fun getThreadList(): ExceptionResult<List<ThreadSummary>> {
            return withContext(ioDispatcher) {
                try {
                    with(ThreadDto) {
                        Ok(threadDao.selectAll().map { it.toModel() })
                    }
                } catch (e: Exception) {
                    Err(e)
                }
            }
        }

        override fun getThreadListFlow(): Flow<ExceptionResult<List<ThreadSummary>>> {
            return threadDao.selectAllFlow().map {
                with(ThreadDto) {
                    Ok(it.map { it.toModel() })
                }
            }
        }

        override suspend fun getThread(threadId: ThreadId): ExceptionResult<ThreadSummary> {
            return withContext(ioDispatcher) {
                try {
                    with(ThreadDto) {
                        val thread = threadDao.selectById(threadId.value)
                        Ok(thread.toModel())
                    }
                } catch (e: Exception) {
                    Err(e)
                }
            }
        }

        override fun getThreadFlow(threadId: ThreadId): Flow<ExceptionResult<ThreadSummary>> {
            return threadDao.selectByIdFlow(threadId.value).map {
                with(ThreadDto) {
                    Ok(it.toModel())
                }
            }
        }

        override suspend fun getThreadDetail(threadId: ThreadId): ExceptionResult<ThreadDetail> {
            return withContext(ioDispatcher) {
                try {
                    with(ThreadDto) {
                        val threadWithMessages = threadDao.selectByIdWithMessages(threadId.value)
                        Ok(threadWithMessages.toModel())
                    }
                } catch (e: Exception) {
                    Err(e)
                }
            }
        }

        override fun getThreadDetailFlow(threadId: ThreadId): Flow<ExceptionResult<ThreadDetail>> {
            return threadDao.selectByIdWithMessagesFlow(threadId.value).map {
                with(ThreadDto) {
                    Ok(it.toModel())
                }
            }
        }

        override suspend fun createThread(thread: ThreadCreation): ExceptionResult<ThreadId> {
            return withContext(ioDispatcher) {
                try {
                    with(ThreadDto) {
                        val id = threadDao.insert(thread.toEntity())
                        Ok(ThreadId(id))
                    }
                } catch (e: Exception) {
                    Err(e)
                }
            }
        }
    }
