package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util

import androidx.annotation.VisibleForTesting
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.foundation.strings.splitIntoWords

object WordsPerMinuteCalculator {

    private const val PAUSE_THRESHOLD_MILLIS = 30_000L
    private const val ONE_MINUTE_MILLIS = 60_000.0

    fun calculateWordsPerMinute(referenceText: String, keystrokes: List<Keystroke>): Double {
        val correctWords = calculateCorrectWords(
            typedText = keystrokes.toTypedStringWithoutErrors(),
            referenceText = referenceText
        )
        val activeMillis = keystrokes.activeTypingDurationMillis()
        val minutes = activeMillis / ONE_MINUTE_MILLIS
        return if (minutes > 0) correctWords / minutes else 0.0
    }

    @VisibleForTesting
    fun List<Keystroke>.toTypedStringWithoutErrors(): String {
        return this
            .filter {
                it.isCorrect || it.isSeparator
            }
            .map { if (it.isSeparator) " " else it.keyCode.toChar() }
            .joinToString("")
    }

    @VisibleForTesting
    fun calculateCorrectWords(typedText: String, referenceText: String): Int {
        val typedWords = typedText.splitIntoWords()
        val referenceWords = referenceText.splitIntoWords()

        return typedWords.zip(referenceWords).count { (typed, reference) ->
            typed == reference
        }
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
