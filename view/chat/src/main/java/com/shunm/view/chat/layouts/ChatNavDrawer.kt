package com.shunm.view.chat.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shunm.common_compose.theme.GeminiChatTheme
import com.shunm.view.chat.R

internal interface DrawerContentScope {
    fun header(header: @Composable DrawerContentHeaderScope.() -> Unit)

    fun content(content: @Composable DrawerContentItemScope.() -> Unit)
}

internal object DrawerContentHeaderScope

internal object DrawerContentItemScope

private class DrawerContentScopeImpl : DrawerContentScope {
    private val headers = mutableListOf<@Composable DrawerContentHeaderScope.() -> Unit>()

    private val contents = mutableListOf<@Composable DrawerContentItemScope.() -> Unit>()

    override fun header(header: @Composable DrawerContentHeaderScope.() -> Unit) {
        headers.add(header)
    }

    override fun content(content: @Composable (DrawerContentItemScope.() -> Unit)) {
        contents.add(content)
    }

    @Composable
    fun Compose() {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
        ) {
            items(headers.size) { index ->
                with(DrawerContentHeaderScope) {
                    headers[index]()
                }
            }
            items(contents.size) { index ->
                with(DrawerContentItemScope) {
                    contents[index]()
                }
            }
        }
    }
}

@Composable
internal fun ChatNavigationDrawer(
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    drawerContent: DrawerContentScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val scope =
                remember {
                    DrawerContentScopeImpl()
                }
            scope.drawerContent()
            scope.Compose()
        },
        content = content,
    )
}

@Composable
internal fun DrawerContentHeaderScope.CreateThreadButton(onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OutlinedButton(
            onClick = onClick,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.view_chat_create_new_thread),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(id = R.string.view_chat_create_new_thread),
            )
        }
    }
}

@Composable
internal fun DrawerContentItemScope.NavigationItem(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        modifier =
            Modifier
                .padding(vertical = 8.dp)
                .clickable(onClick = onClick),
        text = text,
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Preview
@Composable
private fun ChatNavigationDrawerPreview() {
    GeminiChatTheme {
        Surface {
            ChatNavigationDrawer(
                drawerState = rememberDrawerState(DrawerValue.Open),
                drawerContent = {
                    header {
                        CreateThreadButton {
                        }
                    }
                    listOf("Thread1", "Thread2", "Thread3").forEach { thread ->
                        content {
                            NavigationItem(
                                text = thread,
                                onClick = {
                                },
                            )
                        }
                    }
                },
            ) {
            }
        }
    }
}
