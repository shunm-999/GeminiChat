package com.shunm.common_compose.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.shunm.common_compose.theme.GeminiChatTheme

object FilledInputFieldScope

@Composable
fun FilledInputField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    imageList: List<Uri> = emptyList(),
    onImageListChange: (List<Uri>) -> Unit = {},
    onClick: () -> Unit = {},
    placeholder: @Composable (FilledInputFieldScope.() -> Unit)? = null,
    trailingIcon: @Composable (FilledInputFieldScope.() -> Unit)? = null,
) {
    with(FilledInputFieldScope) {
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        LaunchedEffect(isPressed) {
            onClick()
        }
        Column(
            modifier =
                modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                    ),
        ) {
            if (imageList.isNotEmpty()) {
                InputImageRow(
                    imageList = imageList,
                )
            }
            TextField(
                modifier = Modifier,
                value = text,
                onValueChange = onTextChange,
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
                colors = TextFieldDefaults.transparentColors(),
            )
        }
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

@Composable
private fun InputImage(image: Uri) {
    SubcomposeAsyncImage(
        modifier = Modifier.size(40.dp),
        model = image,
        contentDescription = null,
        loading = {
            CircularProgressIndicator()
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InputImageRow(imageList: List<Uri>) {
    FlowRow(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        imageList.forEach { imageUrl ->
            InputImage(imageUrl)
        }
    }
}

@Composable
private fun TextFieldDefaults.transparentColors(): TextFieldColors =
    colors(
        disabledTextColor = Color.Transparent,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
    )

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
                text = input,
                onTextChange = {
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
