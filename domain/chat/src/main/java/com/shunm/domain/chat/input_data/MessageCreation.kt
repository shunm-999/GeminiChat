package com.shunm.domain.chat.input_data

import android.net.Uri
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.ThreadId
import kotlinx.datetime.Instant

data class MessageCreation(
    val threadId: ThreadId,
    val sender: Message.Sender,
    val text: String,
    val imageList: List<Uri>,
    val createAt: Instant,
)
