package com.shunm.infra.database.chat.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "threads")
data class ThreadEntity(
    @PrimaryKey val id: Long,
    val title: String,
    @ColumnInfo(name = "create_at")
    val createAt: Long,
)
