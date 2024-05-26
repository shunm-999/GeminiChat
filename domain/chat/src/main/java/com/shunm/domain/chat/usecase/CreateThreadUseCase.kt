package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.input_data.ThreadCreation
import com.shunm.domain.chat.model.Thread
import com.shunm.domain.chat.repository.ThreadRepository
import javax.inject.Inject

class CreateThreadUseCase
    @Inject
    constructor(
        private val threadRepository: ThreadRepository,
    ) {
        suspend operator fun invoke(creation: ThreadCreation): Thread = threadRepository.createThread(creation)
    }
