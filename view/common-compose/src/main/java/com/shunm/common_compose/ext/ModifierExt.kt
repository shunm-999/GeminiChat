package com.shunm.common_compose.ext

import androidx.compose.ui.Modifier

fun Modifier.useIf(
    condition: Boolean,
    apply: Modifier.() -> Modifier,
): Modifier {
    return if (condition) {
        this.apply()
    } else {
        this
    }
}
