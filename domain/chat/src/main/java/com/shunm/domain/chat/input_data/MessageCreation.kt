package com.shunm.domain.chat.input_data

import android.graphics.Bitmap
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.ThreadId
import kotlinx.datetime.Instant

data class MessageCreation(
    val threadId: ThreadId,
    val sender: Message.Sender,
    val text: String,
    val image: Bitmap? = null,
    val createAt: Instant,
)
