package com.shunm.view.chat.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.shunm.commonCompose.ext.useIf
import com.shunm.commonCompose.theme.GeminiChatTheme
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

internal sealed interface ChatNavigationDrawerContentScope {
    val needNavigationIcon: Boolean

    data class ScopeImpl(
        override val needNavigationIcon: Boolean,
    ) : ChatNavigationDrawerContentScope
}

@Composable
internal fun ChatNavigationLayout(
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    drawerContent: DrawerContentScope.() -> Unit,
    content: @Composable ChatNavigationDrawerContentScope.() -> Unit,
) {
    with(windowAdaptiveInfo) {
        @Composable
        fun ColumnScope.SheetContent() {
            val scope = DrawerContentScopeImpl()
            scope.drawerContent()
            Surface(
                modifier =
                Modifier
                    .fillMaxHeight()
                    .padding(
                        vertical = 8.dp,
                    ),
            ) {
                scope.Compose()
            }
        }

        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED) {
            NavigationRail(
                drawerContent = { SheetContent() },
                content = {
                    content(
                        ChatNavigationDrawerContentScope.ScopeImpl(needNavigationIcon = false),
                    )
                },
            )
        } else {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(
                        content = {
                            SheetContent()
                        },
                    )
                },
                content = {
                    content(
                        ChatNavigationDrawerContentScope.ScopeImpl(needNavigationIcon = true),
                    )
                },
            )
        }
    }
}

@Composable
private fun NavigationRail(
    drawerContent: @Composable ColumnScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier =
            Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .fillMaxHeight()
                .weight(0.3f),
        ) {
            drawerContent()
        }
        Column(
            modifier =
            Modifier
                .fillMaxHeight()
                .weight(0.7f),
        ) {
            content()
        }
    }
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
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        MaterialTheme.colorScheme.primary.copy(
            alpha = 0.2f,
        )
    Box(
        modifier =
        modifier
            .useIf(isSelected) {
                drawBehind {
                    drawRoundRect(
                        color = backgroundColor,
                        cornerRadius = CornerRadius(8.dp.toPx()),
                    )
                }
            }
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .clickable(onClick = onClick),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

private class DrawerPreviewParameterProvider : PreviewParameterProvider<DrawerValue> {
    override val values: Sequence<DrawerValue> =
        sequence {
            yield(DrawerValue.Closed)
            yield(DrawerValue.Open)
        }
}

@PreviewLightDark
@PreviewScreenSizes
@Composable
private fun ChatNavigationDrawerPreview(
    @PreviewParameter(DrawerPreviewParameterProvider::class) drawerValue: DrawerValue,
) {
    GeminiChatTheme {
        Surface {
            ChatNavigationLayout(
                drawerState = rememberDrawerState(drawerValue),
                drawerContent = {
                    header {
                        CreateThreadButton {
                        }
                    }
                    listOf("Thread1", "Thread2", "Thread3").forEach { thread ->
                        content {
                            NavigationItem(
                                isSelected = false,
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
