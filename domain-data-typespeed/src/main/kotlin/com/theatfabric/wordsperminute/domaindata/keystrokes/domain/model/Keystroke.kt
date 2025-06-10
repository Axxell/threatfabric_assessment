package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model

data class Keystroke(
    val gameId: String,
    val keyPressedMillis: Long,
    val keyReleasedMillis: Long,
    val keyCode: Int,
    val isCorrect: Boolean,
    val phoneOrientation: PhoneOrientation,
    val userName: String
)

enum class PhoneOrientation {
    PORTRAIT,
    LANDSCAPE
}