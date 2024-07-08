package com.shunm.infra.database.chat.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shunm.infra.database.chat.entity.ImageEntity

@Dao
interface ImageDao {
    @Query("SELECT * FROM images")
    suspend fun selectAll(): List<ImageEntity>

    @Query("SELECT * FROM images WHERE id = :id")
    suspend fun selectById(id: Long): ImageEntity

    @Query("SELECT * FROM images WHERE message_id = :messageId")
    suspend fun selectByMessageId(messageId: Long): List<ImageEntity>

    @Insert
    suspend fun insert(image: ImageEntity): Long

    @Insert
    suspend fun insert(images: List<ImageEntity>): List<Long>
}
