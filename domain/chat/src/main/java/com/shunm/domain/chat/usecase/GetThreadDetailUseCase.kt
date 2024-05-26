package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.model.ThreadDetail
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.repository.ThreadRepository
import com.shunm.domain.common.Suspend
import com.shunm.domain.common.model.ExceptionResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThreadDetailUseCase
    @Inject
    constructor(
        private val threadRepository: ThreadRepository,
    ) {
        suspend operator fun invoke(
            @Suppress("UNUSED_PARAMETER")
            suspended: Suspend,
            threadId: ThreadId,
        ): ExceptionResult<ThreadDetail> = threadRepository.getThreadDetail(threadId)

        operator fun invoke(threadId: ThreadId): Flow<ExceptionResult<ThreadDetail>> = threadRepository.getThreadDetailFlow(threadId)
    }
