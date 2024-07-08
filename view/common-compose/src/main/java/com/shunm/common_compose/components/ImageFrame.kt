package com.shunm.common_compose.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.compose.SubcomposeAsyncImageScope

data class ImageIconScope(private val parentScope: SubcomposeAsyncImageScope) :
    SubcomposeAsyncImageScope by parentScope

@Composable
fun ImageFrame(
    imageUri: Uri,
    modifier: Modifier = Modifier,
    deleteIcon: (@Composable ImageIconScope.() -> Unit)? = null,
) {
    SubcomposeAsyncImage(
        model = imageUri,
        contentDescription = null,
        clipToBounds = false,
    ) {
        when (painter.state) {
            AsyncImagePainter.State.Empty -> {
            }

            is AsyncImagePainter.State.Error -> {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null,
                )
            }

            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncImagePainter.State.Success -> {
                SubcomposeAsyncImageContent(
                    modifier = modifier,
                )
                deleteIcon?.let {
                    ImageIconScope(this).deleteIcon()
                }
            }
        }
    }
}

@Composable
internal fun ImageIconScope.ImageDeleteIcon(onClick: () -> Unit) {
    Icon(
        modifier =
            Modifier
                .offset(2.dp, (-2).dp)
                .size(12.dp)
                .clip(CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                )
                .clickable {
                    onClick()
                }
                .align(Alignment.TopEnd),
        imageVector = Icons.Default.Close,
        contentDescription = null,
    )
}
