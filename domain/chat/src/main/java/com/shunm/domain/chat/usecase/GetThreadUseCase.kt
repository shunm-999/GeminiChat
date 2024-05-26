package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.model.Thread
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.repository.ThreadRepository
import javax.inject.Inject

class GetThreadUseCase
    @Inject
    constructor(
        private val threadRepository: ThreadRepository,
    ) {
        suspend operator fun invoke(threadId: ThreadId): Thread = threadRepository.getThread(threadId)
    }
