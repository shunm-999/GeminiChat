package com.shunm.infra.database.chat.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.shunm.infra.database.chat.entity.ThreadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThreadDao {
    @Query("SELECT * FROM threads")
    suspend fun selectAll(): List<ThreadEntity>

    @Query("SELECT * FROM threads WHERE id = :id")
    suspend fun selectById(id: Long): ThreadEntity

    @Query("SELECT * FROM threads WHERE id = :id")
    fun selectByIdFlow(id: Long): Flow<ThreadEntity>

    @Insert
    suspend fun insert(thread: ThreadEntity): Long

    @Delete
    suspend fun delete(thread: ThreadEntity)

    @Update
    suspend fun update(thread: ThreadEntity)
}
