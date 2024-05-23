package com.shunm.domain.common.coroutine

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val niaDispatcher: BasicDispatchers)

enum class BasicDispatchers {
    Default,
    IO,
}
