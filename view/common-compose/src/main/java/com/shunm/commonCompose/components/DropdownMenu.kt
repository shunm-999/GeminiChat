package com.shunm.commonCompose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DropdownMenuScope(
    val onDismissRequest: () -> Unit,
) {
    inline fun item(
        content: DropdownMenuScope.() -> Unit,
    ) {
        content()
    }

    inline fun <T> items(
        items: List<T>,
        content: DropdownMenuScope.(T) -> Unit,
    ) {
        items.forEach {
            content(it)
        }
    }
}

@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable DropdownMenuScope.() -> Unit,
) {
    androidx.compose.material3.DropdownMenu(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)),
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        content(
            DropdownMenuScope(
                onDismissRequest = onDismissRequest,
            )
        )
    }
}

@Composable
fun DropdownMenuScope.DropdownMenuItem(
    text: String,
    onClick: () -> Unit,
) {
    androidx.compose.material3.DropdownMenuItem(
        onClick = {
            onClick()
            onDismissRequest()
        },
        text = {
            Text(
                text = text,
            )
        }
    )
}