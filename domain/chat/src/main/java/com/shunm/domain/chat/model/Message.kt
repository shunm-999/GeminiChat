package com.shunm.domain.chat.model

import android.graphics.Bitmap
import kotlinx.datetime.Instant

data class MessageId(val value: Long)

data class Message(
    val id: MessageId,
    val sender: Sender,
    val text: String,
    val image: Bitmap? = null,
    val createAt: Instant,
) {
    sealed interface Sender {
        data class User(
            val user: com.shunm.domain.chat.model.User,
        ) : Sender

        data object Model : Sender
    }
}
