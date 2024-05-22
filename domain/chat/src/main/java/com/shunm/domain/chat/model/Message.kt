package com.shunm.domain.chat.model

import android.graphics.Bitmap

data class Message(
    val sender: Sender,
    val text: String,
    val image: Bitmap? = null
) {
    sealed interface Sender {
        data class User(
            val user: com.shunm.domain.chat.model.User
        ) : Sender

        data object Model : Sender
    }
}