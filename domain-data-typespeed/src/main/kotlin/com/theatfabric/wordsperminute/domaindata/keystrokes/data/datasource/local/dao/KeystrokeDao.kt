package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import kotlinx.coroutines.flow.Flow

@Dao
internal interface KeystrokeDao {

    @Insert
    suspend fun addKeystroke(keystroke: KeystrokeDto)

    @Query("SELECT * FROM keystrokes ORDER BY key_pressed ASC")
    suspend fun getAll(): List<KeystrokeDto>

    @Query("SELECT * FROM keystrokes ORDER BY key_pressed ASC")
    fun observeAll(): Flow<List<KeystrokeDto>>

    @Query("SELECT * FROM keystrokes WHERE game_id = :gameId ORDER BY key_pressed ASC")
    suspend fun getGameKeystrokes(gameId: String): List<KeystrokeDto>

    @Query("SELECT * FROM keystrokes WHERE game_id = :gameId ORDER BY key_pressed ASC")
    fun observeGameKeystrokes(gameId: String): Flow<List<KeystrokeDto>>

    @Query("DELETE FROM keystrokes WHERE game_id = :gameId")
    suspend fun deleteGameKeystrokes(gameId: String)

    @Query("DELETE FROM keystrokes")
    suspend fun deleteAll()

}