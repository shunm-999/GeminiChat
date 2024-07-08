package com.shunm.infra.database.chat.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.shunm.infra.database.chat.entity.MessageEntity
import com.shunm.infra.database.chat.entity.MessageWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Transaction
    @Query("SELECT * FROM messages")
    suspend fun selectAll(): List<MessageWithImages>

    @Transaction
    @Query("SELECT * FROM messages WHERE id = :id")
    suspend fun selectById(id: Int): MessageWithImages

    @Transaction
    @Query("SELECT * FROM messages WHERE thread_id = :threadId")
    suspend fun selectByThreadId(threadId: Long): List<MessageWithImages>

    @Transaction
    @Query("SELECT * FROM messages WHERE thread_id = :threadId")
    fun selectByThreadIdFlow(threadId: Long): Flow<List<MessageWithImages>>

    @Insert
    suspend fun insert(message: MessageEntity): Long

    @Delete
    suspend fun delete(message: MessageEntity)

    @Update
    suspend fun update(message: MessageEntity)
}
