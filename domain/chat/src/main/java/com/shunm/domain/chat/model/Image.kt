package com.shunm.domain.chat.model

import android.net.Uri

data class ImageId(val value: Long)

data class Image(
    val id: ImageId,
    val imageUri: Uri,
)
