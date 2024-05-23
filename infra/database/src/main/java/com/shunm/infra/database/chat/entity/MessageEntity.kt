package com.shunm.infra.database.chat.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: Int,
    val threadId: Int,
    val sender: SenderType,
    val text: String,
) {
    enum class SenderType {
        USER,
        MODEL,
    }

    object MessageEntityConverter {
        @TypeConverter
        fun fromSenderType(value: SenderType): String {
            return value.name
        }

        @TypeConverter
        fun toSenderType(value: String): SenderType {
            return enumValueOf(value)
        }
    }
}
