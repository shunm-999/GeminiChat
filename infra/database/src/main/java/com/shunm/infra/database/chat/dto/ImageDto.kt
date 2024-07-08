package com.shunm.infra.database.chat.dto

import com.shunm.domain.chat.input_data.MessageCreation
import com.shunm.domain.chat.model.Image
import com.shunm.domain.chat.model.Message
import com.shunm.infra.database.chat.entity.ImageEntity

object ImageDto {
    fun MessageCreation.toEntityList(messageId: Long): List<ImageEntity> {
        return this.imageList.map {
            ImageEntity(
                id = 0,
                messageId = messageId,
                url = it.toString(),
            )
        }
    }

    fun Message.toEntityList(): List<ImageEntity> {
        return this.imageList.map { it.toEntity() }
    }

    private fun Image.toEntity(): ImageEntity {
        return ImageEntity(
            id = this.id.value,
            messageId = 0,
            url = this.imageUri.toString(),
        )
    }
}
