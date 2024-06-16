package com.shunm.view.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shunm.common_compose.components.FilledInputField
import com.shunm.common_compose.components.PlaceHolder
import com.shunm.common_compose.components.VoiceRecognitionIcon
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.view.chat.R

@Composable
internal fun ChatInputField(
    text: String,
    onTextChange: (String) -> Unit,
    onSubmit: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier =
            modifier
                .padding(
                    vertical = 4.dp,
                    horizontal = 16.dp,
                )
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        FilledInputField(
            value = text,
            onValueChange = onTextChange,
            modifier =
                Modifier
                    .weight(1f),
            placeholder = {
                PlaceHolder(
                    text = stringResource(id = R.string.view_chat_input_field_placeholder),
                )
            },
            trailingIcon = {
                VoiceRecognitionIcon { }
            },
        )
        IconButton(
            onClick = {
                keyboardController?.hide()
                onSubmit(text)
            },
            modifier =
                Modifier
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
        ) {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface,
            )
        }
    }
}

@Preview
@Composable
private fun ChatInputFieldPreview() {
    GeminiChatTheme {
        Surface {
            ChatInputField(
                text = "",
                onTextChange = { },
                onSubmit = { },
            )
        }
    }
}
