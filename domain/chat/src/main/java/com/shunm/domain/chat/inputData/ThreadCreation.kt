package com.shunm.domain.chat.inputData

import kotlinx.datetime.Instant

data class ThreadCreation(
    val title: String,
    val createAt: Instant,
)
