package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource

import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto

internal interface TypeSpeedSource {

    fun addKeystroke(keystroke: KeystrokeDto)

    fun getAllKeystrokes(): List<KeystrokeDto>

    fun getGameKeystrokes(gameId: String): List<KeystrokeDto>

    fun deleteKeystrokesForGame(gameId: String)

    fun deleteAllKeystrokes()

}