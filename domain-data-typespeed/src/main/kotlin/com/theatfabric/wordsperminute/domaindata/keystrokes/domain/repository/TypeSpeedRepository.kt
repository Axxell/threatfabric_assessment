package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository

import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke

interface TypeSpeedRepository {

    suspend fun saveKeystroke(keystroke: Keystroke)

}