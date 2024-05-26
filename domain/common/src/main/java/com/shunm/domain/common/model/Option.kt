package com.shunm.domain.common.model

sealed interface Option<out T>

data class Some<T>(val value: T) : Option<T>

data object None : Option<Nothing>
