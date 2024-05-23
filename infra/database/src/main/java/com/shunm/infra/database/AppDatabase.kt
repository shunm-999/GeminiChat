package com.shunm.infra.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shunm.infra.database.chat.dao.MessageDao
import com.shunm.infra.database.chat.dao.ThreadDao
import com.shunm.infra.database.chat.entity.MessageEntity
import com.shunm.infra.database.chat.entity.ThreadEntity

@Database(entities = [ThreadEntity::class, MessageEntity::class], version = 1)
@TypeConverters(MessageEntity.MessageEntityConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun threadDao(): ThreadDao

    abstract fun messageDao(): MessageDao
}
