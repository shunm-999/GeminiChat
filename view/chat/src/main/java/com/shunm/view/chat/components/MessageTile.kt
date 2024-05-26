package com.shunm.view.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.domain.chat.model.Message
import com.shunm.domain.chat.model.MessageId
import com.shunm.domain.chat.model.User
import com.shunm.view.chat.R
import kotlinx.datetime.Instant

@Composable
internal fun MessageTile(
    modifier: Modifier = Modifier,
    message: Message,
) {
    MessageTileFrame(
        modifier = modifier,
        icon = {
            when (val sender = message.sender) {
                is Message.Sender.User ->
                    UserIcon(
                        modifier = Modifier.size(20.dp),
                        sender = sender,
                    )

                is Message.Sender.Model ->
                    ModelIcon(
                        modifier = Modifier.size(20.dp),
                    )
            }
        },
        sender = {
            SenderName(
                sender = message.sender,
            )
        },
        message = {
            MessageContent(
                text = message.text,
            )
        },
    )
}

@Composable
private fun MessageTileFrame(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    sender: @Composable () -> Unit,
    message: @Composable () -> Unit,
) {
    var iconWidth by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    Surface {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier =
                        Modifier.onGloballyPositioned {
                            with(density) {
                                iconWidth = it.size.width.toDp()
                            }
                        },
                ) {
                    icon()
                }
                Spacer(modifier = Modifier.width(8.dp))
                sender()
            }
            Box(modifier = Modifier.padding(start = iconWidth + 8.dp)) {
                message()
            }
        }
    }
}

@Composable
private fun UserIcon(
    modifier: Modifier = Modifier,
    sender: Message.Sender.User,
) {
    NameIcon(
        modifier = modifier,
        name = sender.user.name,
    )
}

@Composable
private fun ModelIcon(modifier: Modifier = Modifier) {
    NameIcon(
        modifier = modifier,
        name = stringResource(id = R.string.view_chat_gemini_model_name),
    )
}

@Composable
private fun NameIcon(
    modifier: Modifier = Modifier,
    name: String,
    style: TextStyle = MaterialTheme.typography.labelSmall,
) {
    Surface(
        modifier = modifier.clip(shape = CircleShape),
        color = MaterialTheme.colorScheme.primary,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            val initial = name.firstOrNull() ?: ""
            Text(
                text = "$initial",
                style = style,
            )
        }
    }
}

@Composable
private fun SenderName(
    modifier: Modifier = Modifier,
    sender: Message.Sender,
    style: TextStyle = MaterialTheme.typography.labelSmall,
) {
    Text(
        modifier = modifier,
        text =
            when (sender) {
                is Message.Sender.User -> sender.user.name
                is Message.Sender.Model -> stringResource(id = R.string.view_chat_gemini_model_name)
            },
        style = style,
    )
}

@Composable
private fun MessageContent(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
    )
}

@PreviewLightDark
@Composable
private fun MessageTilePreview() {
    GeminiChatTheme {
        MessageTile(
            message =
                Message(
                    id = MessageId(1),
                    sender =
                        Message.Sender.User(
                            User(
                                name = "User",
                            ),
                        ),
                    text = "Hello, World!",
                    createAt = Instant.DISTANT_PAST,
                ),
        )
    }
}

@PreviewLightDark
@Composable
private fun UserIconPreview() {
    GeminiChatTheme {
        UserIcon(
            modifier = Modifier.size(40.dp),
            sender =
                Message.Sender.User(
                    User(
                        name = "User",
                    ),
                ),
        )
    }
}

@PreviewLightDark
@Composable
private fun ModelIconPreview() {
    GeminiChatTheme {
        ModelIcon(
            modifier = Modifier.size(40.dp),
        )
    }
}
