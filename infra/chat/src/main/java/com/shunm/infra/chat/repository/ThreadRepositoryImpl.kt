package com.shunm.infra.chat.repository

import com.shunm.infra.database.AppDatabase
import javax.inject.Inject

internal class ThreadRepositoryImpl
    @Inject
    constructor(
        private val database: AppDatabase,
    ) {
        private val threadDao = database.threadDao()
    }
