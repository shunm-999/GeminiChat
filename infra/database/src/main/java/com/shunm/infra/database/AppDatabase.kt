package com.shunm.infra.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shunm.infra.database.chat.dao.ImageDao
import com.shunm.infra.database.chat.dao.MessageDao
import com.shunm.infra.database.chat.dao.ThreadDao
import com.shunm.infra.database.chat.entity.ImageEntity
import com.shunm.infra.database.chat.entity.MessageEntity
import com.shunm.infra.database.chat.entity.ThreadEntity

@Database(
    version = 2,
    entities = [ThreadEntity::class, MessageEntity::class, ImageEntity::class],
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
)
@TypeConverters(MessageEntity.MessageEntityConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun threadDao(): ThreadDao

    abstract fun messageDao(): MessageDao

    abstract fun imageDao(): ImageDao
}
