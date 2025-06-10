package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model

data class LoggedKeyEvent(
    val keyChar: Char,
    val keyCode: Int,
    val keyPressedMillis: Long,
    val keyReleasedMillis: Long,
    val isCorrect: Boolean,
    val isSeparator: Boolean
)
