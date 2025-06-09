package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local

import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.TypeSpeedSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.database.TypeSpeedDatabase
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import javax.inject.Inject

internal class TypeSpeedLocalSource @Inject constructor(
    typeSpeedDatabase: TypeSpeedDatabase
) : TypeSpeedSource {

    private val keystrokeDao = typeSpeedDatabase.keystrokeDao()

    override fun addKeystroke(keystroke: KeystrokeDto) {
        keystrokeDao.addKeystroke(keystroke)
    }

    override fun getAllKeystrokes(): List<KeystrokeDto> {
        return keystrokeDao.getAll()
    }

    override fun getGameKeystrokes(gameId: String): List<KeystrokeDto> {
        return keystrokeDao.getGameKeystrokes(gameId)
    }

    override fun deleteKeystrokesForGame(gameId: String) {
        keystrokeDao.deleteGameKeystrokes(gameId)
    }

    override fun deleteAllKeystrokes() {
        keystrokeDao.deleteAll()
    }
}