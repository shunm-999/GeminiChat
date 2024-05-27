package com.shunm.domain.common.ktx

import kotlinx.datetime.Instant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Instant.format(pattern: String): String =
    SimpleDateFormat(
        pattern,
        Locale.getDefault(),
    ).format(Date(toEpochMilliseconds()))
