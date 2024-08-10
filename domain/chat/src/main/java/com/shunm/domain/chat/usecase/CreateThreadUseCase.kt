package com.shunm.domain.chat.usecase

import com.shunm.domain.chat.inputData.ThreadCreation
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.repository.ThreadRepository
import com.shunm.domain.common.model.ExceptionResult
import javax.inject.Inject

class CreateThreadUseCase
@Inject
constructor(
    private val threadRepository: ThreadRepository,
) {
    suspend operator fun invoke(creation: ThreadCreation): ExceptionResult<ThreadId> = threadRepository.createThread(creation)
}
