package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository

import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import kotlinx.coroutines.flow.Flow

interface TypeSpeedRepository {

    suspend fun saveKeystroke(keystroke: Keystroke)

    suspend fun getGameKeystrokes(gameId: String): List<Keystroke>

    fun observeGameKeystrokes(gameId: String): Flow<List<Keystroke>>

    suspend fun deleteGameKeystrokes(gameId: String)

    suspend fun deleteAll()

}