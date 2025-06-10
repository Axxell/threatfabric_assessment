package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util

import androidx.annotation.VisibleForTesting
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.foundation.strings.isSeparator

object WordsPerMinuteCalculator {

    private const val PAUSE_THRESHOLD_MILLIS = 15_000L
    private const val ONE_MINUTE_MILLIS = 60_000.0

    fun calculateWordsPerMinute(referenceText: String, keystrokes: List<Keystroke>): Double {
        val correctWords = calculateCorrectWords(
            keystrokes = keystrokes,
            referenceText = referenceText
        )
        val activeMillis = keystrokes.activeTypingDurationMillis()
        val minutes = activeMillis / ONE_MINUTE_MILLIS
        return if (minutes > 0) correctWords / minutes else 0.0
    }

    @VisibleForTesting
    fun calculateCorrectWords(keystrokes: List<Keystroke>, referenceText: String): Int {
        var wordsCount = 0
        var isWordFailed = false
        var word = ""
        keystrokes.forEachIndexed { index, keystroke ->
            val referenceChar = referenceText[index]

            if (referenceChar.isSeparator()) {
                if (!isWordFailed && word.isNotEmpty()) {
                    wordsCount++
                }
                isWordFailed = false
                word = ""
            } else {
                if (keystroke.isCorrect) {
                    word += keystroke.keyCode.toChar()
                } else {
                    isWordFailed = true
                }
            }
        }

        return wordsCount
    }

    @VisibleForTesting
    fun List<Keystroke>.activeTypingDurationMillis(): Long {
        if (this.isEmpty()) return 0L
        var activeTime = 0L
        var lastTime = first().keyPressedMillis

        forEach { keystroke ->
            val keyPressedCurrent = keystroke.keyPressedMillis
            val gap = keyPressedCurrent - lastTime

            // Only add gap if it's less than pause threshold
            if (gap < PAUSE_THRESHOLD_MILLIS) {
                activeTime += gap
            }

            lastTime = keyPressedCurrent
        }

        return activeTime
    }

}
