package com.shunm.infra.database.chat.di

import android.content.Context
import androidx.room.Room
import com.shunm.infra.database.AppDatabase
import com.shunm.infra.database.chat.dao.ImageDao
import com.shunm.infra.database.chat.dao.MessageDao
import com.shunm.infra.database.chat.dao.ThreadDao
import com.shunm.infra.database.util.TransactionManager
import com.shunm.infra.database.util.TransactionManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "gemini-chat-database",
        ).build()

    @Provides
    @Singleton
    fun provideTransactionManager(appDatabase: AppDatabase): TransactionManager = TransactionManagerImpl(appDatabase)

    @Provides
    @Singleton
    fun providesThreadDao(appDatabase: AppDatabase): ThreadDao = appDatabase.threadDao()

    @Provides
    @Singleton
    fun providesMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()

    @Provides
    @Singleton
    fun providesImageDao(appDatabase: AppDatabase): ImageDao = appDatabase.imageDao()
}
