package com.shunm.infra.database.chat.dto

import com.shunm.domain.chat.inputData.ThreadCreation
import com.shunm.domain.chat.model.ThreadDetail
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.model.ThreadSummary
import com.shunm.infra.database.chat.dto.MessageDto.toModel
import com.shunm.infra.database.chat.entity.ThreadEntity
import com.shunm.infra.database.chat.entity.ThreadWithMessages
import kotlinx.datetime.Instant

object ThreadDto {
    fun ThreadCreation.toEntity(): ThreadEntity {
        return ThreadEntity(
            id = 0,
            title = this.title,
            createAt = this.createAt.toEpochMilliseconds(),
        )
    }

    fun ThreadEntity.toModel(): ThreadSummary {
        return ThreadSummary(
            id = ThreadId(this.id),
            title = this.title,
            createAt = Instant.fromEpochMilliseconds(this.createAt),
        )
    }

    fun ThreadWithMessages.toModel(): ThreadDetail {
        val thread = this.thread
        val messages = this.messageWithImages.map { it.toModel() }
        return ThreadDetail(
            id = ThreadId(thread.id),
            title = thread.title,
            messages = messages,
            createAt = Instant.fromEpochMilliseconds(thread.createAt),
        )
    }
}
