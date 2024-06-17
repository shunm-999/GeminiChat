package com.shunm.view.chat.components

import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
    imageList: List<Uri> = emptyList(),
    optionVisible: Boolean = true,
    optionVisibleChange: (Boolean) -> Unit = {},
    onClickCamera: () -> Unit = {},
    onClickPhoto: () -> Unit = {},
    onClickFolder: () -> Unit = {},
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
        AnimatedContent(targetState = optionVisible, label = "") { isVisible ->
            if (isVisible) {
                Options(
                    onClickCamera = onClickCamera,
                    onClickPhoto = onClickPhoto,
                    onClickFolder = onClickFolder,
                )
            } else {
                OptionAddButton(
                    onClick = {
                        optionVisibleChange(true)
                    },
                )
            }
        }
        FilledInputField(
            modifier = Modifier.weight(1f),
            text = text,
            onTextChange = onTextChange,
            imageList = imageList,
            onClick = {
                optionVisibleChange(false)
            },
            placeholder = {
                PlaceHolder(
                    text = stringResource(id = R.string.view_chat_input_field_placeholder),
                )
            },
            trailingIcon = {
                VoiceRecognitionIcon { }
            },
        )
        SubmitButton(
            onClick = {
                keyboardController?.hide()
                onSubmit(text)
            },
        )
    }
}

@Composable
private fun Options(
    onClickCamera: () -> Unit,
    onClickPhoto: () -> Unit,
    onClickFolder: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier =
                Modifier.clickable {
                    onClickCamera()
                },
            imageVector = Icons.Outlined.PhotoCamera,
            contentDescription = null,
        )
        Icon(
            modifier =
                Modifier.clickable {
                    onClickPhoto()
                },
            imageVector = Icons.Outlined.Photo,
            contentDescription = null,
        )
        Icon(
            modifier =
                Modifier.clickable {
                    onClickFolder()
                },
            imageVector = Icons.Outlined.Folder,
            contentDescription = null,
        )
    }
}

@Composable
private fun SubmitButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
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

@Composable
private fun OptionAddButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier =
            Modifier
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                ),
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}

private data class ChatInputFieldPreviewContext(
    val text: String,
    val optionVisible: Boolean,
    val imageList: List<Uri>,
)

private class ChatInputFieldPreviewParameterProvider :
    PreviewParameterProvider<ChatInputFieldPreviewContext> {
    private val textList = listOf<String>("", "Hello, World!")

    private val optionVisibleList = listOf<Boolean>(true, false)

    private val imageList =
        listOf<List<Uri>>(
            emptyList(),
            listOf(
                Uri.parse("https://picsum.photos/200/300"),
            ),
        )

    override val values: Sequence<ChatInputFieldPreviewContext> =
        sequence {
            for (text in textList) {
                for (optionVisible in optionVisibleList) {
                    for (image in imageList) {
                        yield(
                            ChatInputFieldPreviewContext(
                                text = text,
                                optionVisible = optionVisible,
                                imageList = image,
                            ),
                        )
                    }
                }
            }
        }
}

@PreviewLightDark
@Composable
private fun ChatInputFieldPreview(
    @PreviewParameter(ChatInputFieldPreviewParameterProvider::class) context: ChatInputFieldPreviewContext,
) {
    GeminiChatTheme {
        Surface {
            var optionVisible: Boolean by remember { mutableStateOf(context.optionVisible) }
            ChatInputField(
                text = context.text,
                onTextChange = { },
                imageList = context.imageList,
                onSubmit = { },
                optionVisible = optionVisible,
                optionVisibleChange = {
                    optionVisible = it
                },
            )
        }
    }
}
