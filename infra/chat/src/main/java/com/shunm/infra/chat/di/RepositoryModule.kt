package com.shunm.infra.chat.di

import com.shunm.domain.chat.repository.GeminiRepository
import com.shunm.domain.chat.repository.ImageRepository
import com.shunm.domain.chat.repository.MessageRepository
import com.shunm.domain.chat.repository.ThreadRepository
import com.shunm.infra.chat.repository.GeminiRepositoryImpl
import com.shunm.infra.chat.repository.ImageRepositoryImpl
import com.shunm.infra.chat.repository.MessageRepositoryImpl
import com.shunm.infra.chat.repository.ThreadRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    fun bindThreadRepository(threadRepositoryImpl: ThreadRepositoryImpl): ThreadRepository

    @Binds
    fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

    @Binds
    fun bindGeminiRepository(geminiRepositoryImpl: GeminiRepositoryImpl): GeminiRepository

    @Binds
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository
}
