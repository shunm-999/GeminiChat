package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.model.ThreadSummary
import com.shunm.domain.chat.repository.ThreadRepository
import com.shunm.domain.common.model.ExceptionResult
import javax.inject.Inject

class GetThreadUseCase
    @Inject
    constructor(
        private val threadRepository: ThreadRepository,
    ) {
        suspend operator fun invoke(threadId: ThreadId): ExceptionResult<ThreadSummary> = threadRepository.getThread(threadId)
    }
