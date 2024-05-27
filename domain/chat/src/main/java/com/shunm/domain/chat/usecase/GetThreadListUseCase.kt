package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.model.ThreadSummary
import com.shunm.domain.chat.repository.ThreadRepository
import com.shunm.domain.common.Suspend
import com.shunm.domain.common.model.ExceptionResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThreadListUseCase
    @Inject
    constructor(
        private val threadRepository: ThreadRepository,
    ) {
        suspend operator fun invoke(
            @Suppress("UNUSED_PARAMETER")
            suspended: Suspend,
        ): ExceptionResult<List<ThreadSummary>> = threadRepository.getThreadList()

        operator fun invoke(): Flow<ExceptionResult<List<ThreadSummary>>> = threadRepository.getThreadListFlow()
    }
