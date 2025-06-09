package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto

@Dao
internal interface KeystrokeDao {

    @Insert
    fun addKeystroke(keystroke: KeystrokeDto)

    @Query("SELECT * FROM keystrokes")
    fun getAll(): List<KeystrokeDto>

    @Query("SELECT * FROM keystrokes WHERE game_id = :gameId")
    fun getGameKeystrokes(gameId: String): List<KeystrokeDto>

    @Query("DELETE FROM keystrokes WHERE game_id = :gameId")
    fun deleteGameKeystrokes(gameId: String)

    @Query("DELETE FROM keystrokes")
    fun deleteAll()

}