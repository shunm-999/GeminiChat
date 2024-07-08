package com.shunm.infra.database.util

import androidx.room.withTransaction
import com.shunm.infra.database.AppDatabase

interface TransactionManager {
    suspend fun <R> withTransaction(block: suspend () -> R): R
}

class TransactionManagerImpl(
    private val appDatabase: AppDatabase,
) : TransactionManager {
    override suspend fun <R> withTransaction(block: suspend () -> R): R {
        return appDatabase.withTransaction {
            block()
        }
    }
}
