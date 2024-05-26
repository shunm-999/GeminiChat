package com.shunm.domain.chat.input_data

import kotlinx.datetime.Instant

data class ThreadCreation(
    val title: String,
    val createAt: Instant,
)
