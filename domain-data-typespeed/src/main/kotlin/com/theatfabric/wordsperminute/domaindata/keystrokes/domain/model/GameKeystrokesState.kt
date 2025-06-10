package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model

data class GameKeystrokesState(
    val gameId: String?,
    val keystrokes: List<Keystroke>
)
