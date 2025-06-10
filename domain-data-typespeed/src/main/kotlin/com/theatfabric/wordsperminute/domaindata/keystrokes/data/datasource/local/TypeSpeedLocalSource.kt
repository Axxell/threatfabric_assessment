package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local

import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.TypeSpeedSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dao.KeystrokeDao
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TypeSpeedLocalSource @Inject constructor(
    private val keystrokeDao: KeystrokeDao
) : TypeSpeedSource {

    override suspend fun addKeystroke(keystroke: KeystrokeDto) {
        keystrokeDao.addKeystroke(keystroke)
    }

    override suspend fun getGameKeystrokes(gameId: String): List<KeystrokeDto> {
        return keystrokeDao.getGameKeystrokes(gameId)
    }

    override fun observeGameKeystrokes(gameId: String): Flow<List<KeystrokeDto>> {
        return keystrokeDao.observeGameKeystrokes(gameId)
    }

    override suspend fun deleteKeystrokesForGame(gameId: String) {
        keystrokeDao.deleteGameKeystrokes(gameId)
    }

    override suspend fun deleteAllKeystrokes() {
        keystrokeDao.deleteAll()
    }
}