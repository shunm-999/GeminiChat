package com.shunm.common_compose.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.shunm.common_compose.theme.GeminiChatTheme

object FilledInputFieldScope

@Composable
fun FilledInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    placeholder:
        @Composable()
        (FilledInputFieldScope.() -> Unit)? = null,
    trailingIcon:
        @Composable()
        (FilledInputFieldScope.() -> Unit)? = null,
) {
    with(FilledInputFieldScope) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        LaunchedEffect(isPressed) {
            onClick()
        }

        TextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            placeholder =
                placeholder?.let {
                    { it() }
                },
            trailingIcon =
                trailingIcon?.let {
                    { it() }
                },
            interactionSource = interactionSource,
            shape = RoundedCornerShape(16.dp),
            colors =
                TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
        )
    }
}

@Composable
fun FilledInputFieldScope.PlaceHolder(text: String) {
    Text(text = text)
}

@Composable
fun FilledInputFieldScope.VoiceRecognitionIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.KeyboardVoice,
            contentDescription = null,
        )
    }
}

private class FilledInputFieldPreviewParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> =
        sequence {
            yield("")
            yield("Hello, Compose!")
        }
}

@PreviewLightDark
@Composable
private fun FilledInputFieldPreview(
    @PreviewParameter(FilledInputFieldPreviewParameterProvider::class) text: String,
) {
    GeminiChatTheme {
        var input by remember {
            mutableStateOf(text)
        }
        Surface {
            FilledInputField(
                value = input,
                onValueChange = {
                    input = it
                },
                placeholder = {
                    PlaceHolder(text = "Placeholder")
                },
                trailingIcon = {
                    VoiceRecognitionIcon { }
                },
            )
        }
    }
}
