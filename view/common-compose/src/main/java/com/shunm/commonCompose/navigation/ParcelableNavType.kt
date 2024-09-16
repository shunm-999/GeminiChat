package com.shunm.commonCompose.navigation

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@OptIn(InternalSerializationApi::class)
inline fun <reified T : Parcelable> parcelableNavType(isNullableAllowed: Boolean = false) =
    object : NavType<T>(
        isNullableAllowed = isNullableAllowed,
    ) {
        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putParcelable(key, value)
        }

        override fun get(bundle: Bundle, key: String): T {
            return bundle.getParcelableCompat<T>(key)!!
        }

        override fun serializeAsValue(value: T): String {
            return Json.encodeToString(T::class.serializer(), value).encodeUri()
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString<T>(value.decodeUri())
        }
    }

inline fun <reified T : Parcelable> Bundle.getParcelableCompat(key: String): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        getParcelable(key)
    }
}

fun String.encodeUri(): String {
    return Uri.encode(this)
}

fun String.decodeUri(): String {
    return Uri.decode(this)
}
