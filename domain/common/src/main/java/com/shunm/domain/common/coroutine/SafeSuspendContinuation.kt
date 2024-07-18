package com.shunm.domain.common.coroutine

import com.shunm.domain.common.model.Result
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine

sealed interface SafeSuspendContinuation<T, E> {
    fun resumeWith(result: Result<T, E>)

    fun cancel()
}

private data class SafeSuspendContinuationImpl<T, E>(
    val continuation: CancellableContinuation<Result<T, E>>,
    val callback: () -> Unit,
    val canResume: () -> Boolean,
) : SafeSuspendContinuation<T, E> {
    override fun resumeWith(result: Result<T, E>) {
        callback()
        if (continuation.isActive && canResume()) {
            continuation.resumeWith(kotlin.Result.success(result))
        }
    }

    override fun cancel() {
        if (continuation.isActive) {
            continuation.cancel()
        }
    }
}

suspend fun <T, E> safeSuspendCoroutine(block: (SafeSuspendContinuation<T, E>) -> Unit): Result<T, E> {
    var isResumed = false
    return suspendCancellableCoroutine { continuation ->
        val suspendContinuation =
            SafeSuspendContinuationImpl(
                continuation = continuation,
                callback = { isResumed = true },
                canResume = { isResumed },
            )
        block(suspendContinuation)
    }
}
