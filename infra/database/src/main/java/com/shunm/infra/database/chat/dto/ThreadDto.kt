package com.shunm.infra.database.chat.dto

import com.shunm.domain.chat.model.Thread
import com.shunm.domain.chat.model.ThreadId
import com.shunm.infra.database.chat.dto.MessageDto.toModel
import com.shunm.infra.database.chat.entity.ThreadEntity
import com.shunm.infra.database.chat.entity.ThreadWithMessages
import kotlinx.datetime.Instant

object ThreadDto {
    fun Thread.toEntity(): ThreadEntity {
        return ThreadEntity(
            id = this.id.value,
            title = this.title,
            createAt = this.createAt.toEpochMilliseconds(),
        )
    }

    fun ThreadWithMessages.toModel(): Thread {
        val thread = this.thread
        val message = this.messages.map { it.toModel() }
        return Thread(
            id = ThreadId(thread.id),
            title = thread.title,
            messages = message,
            createAt = Instant.fromEpochMilliseconds(thread.createAt),
        )
    }
}
