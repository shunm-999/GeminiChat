package com.shunm.infra.database.chat.dto

import com.shunm.domain.chat.input_data.ThreadCreation
import com.shunm.domain.chat.model.ThreadId
import com.shunm.domain.chat.model.ThreadSummary
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

    fun ThreadWithMessages.toModel(): ThreadSummary {
        val thread = this.thread
        return ThreadSummary(
            id = ThreadId(thread.id),
            title = thread.title,
            createAt = Instant.fromEpochMilliseconds(thread.createAt),
        )
    }
}
