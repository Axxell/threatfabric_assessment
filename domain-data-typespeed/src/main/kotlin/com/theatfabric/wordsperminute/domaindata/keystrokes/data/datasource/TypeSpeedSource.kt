package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource

import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import kotlinx.coroutines.flow.Flow

internal interface TypeSpeedSource {

    suspend fun addKeystroke(keystroke: KeystrokeDto)

    suspend fun getGameKeystrokes(gameId: String): List<KeystrokeDto>

    fun observeGameKeystrokes(gameId: String): Flow<List<KeystrokeDto>>

    suspend fun deleteKeystrokesForGame(gameId: String)

    suspend fun deleteAllKeystrokes()

}