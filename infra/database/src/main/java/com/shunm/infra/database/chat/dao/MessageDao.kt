package com.shunm.infra.database.chat.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shunm.infra.database.chat.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    suspend fun selectAll(): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun selectById(id: Int): MessageEntity

    @Query("SELECT * FROM messages WHERE thread_id = :threadId")
    suspend fun selectByThreadId(threadId: Long): List<MessageEntity>

    @Query("SELECT * FROM messages WHERE thread_id = :threadId")
    fun selectByThreadIdFlow(threadId: Long): Flow<List<MessageEntity>>

    @Insert
    suspend fun insert(message: MessageEntity)

    @Delete
    suspend fun delete(message: MessageEntity)

    @Update
    suspend fun update(message: MessageEntity)
}
