package com.shunm.infra.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [
        ForeignKey(
            entity = MessageEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("message_id"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "message_id")
    val messageId: Long,
    val url: String,
)
