package com.shunm.domain.chat.input_data

import android.graphics.Bitmap
import com.shunm.domain.chat.model.ThreadId

data class MessageCreation(
    val threadId: ThreadId,
    val text: String,
    val image: Bitmap? = null
)