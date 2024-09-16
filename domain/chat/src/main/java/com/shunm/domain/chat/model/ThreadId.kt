package com.shunm.domain.chat.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ThreadId(val value: Long) : Parcelable {
    companion object {
        val undefined = ThreadId(-1)
    }
}
