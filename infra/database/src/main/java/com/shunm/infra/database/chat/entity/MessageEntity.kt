package com.shunm.infra.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(
    tableName = "messages",
    foreignKeys =
        arrayOf(
            ForeignKey(
                entity = ThreadEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("thread_id"),
                onDelete = ForeignKey.CASCADE,
            ),
        ),
)
data class MessageEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "thread_id")
    val threadId: Long,
    val sender: SenderType,
    val text: String,
    @ColumnInfo(name = "create_at")
    val createAt: Long,
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
