package com.shunm.view.chat.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shunm.domain.chat.model.Message

@Composable
internal fun MessageList(messages: List<Message>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(messages) { message ->
            MessageTile(
                message = message,
            )
        }
    }
}
