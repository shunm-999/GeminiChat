package com.shunm.domain.chat.input_data

import com.shunm.domain.chat.model.ThreadId

data class ThreadCreation(
    val id: ThreadId,
    val title: String
)