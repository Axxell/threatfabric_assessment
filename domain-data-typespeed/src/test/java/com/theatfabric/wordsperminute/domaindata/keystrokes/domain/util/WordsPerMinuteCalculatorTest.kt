package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util

import com.google.common.truth.Truth.assertThat
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util.WordsPerMinuteCalculator.activeTypingDurationMillis
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util.WordsPerMinuteCalculator.toTypedStringWithoutErrors
import org.junit.jupiter.api.Test

class WordsPerMinuteCalculatorTest {

    private val gameId = "game-123"
    private val userName = "testUser"

    private fun keystroke(
        char: Char,
        pressedAt: Long,
        isCorrect: Boolean = true,
        isSeparator: Boolean = false
    ): Keystroke {
        return Keystroke(
            gameId = gameId,
            keyPressedMillis = pressedAt,
            keyReleasedMillis = pressedAt + 100,
            keyCode = char.code,
            isCorrect = isCorrect,
            isSeparator = isSeparator,
            phoneOrientation = PhoneOrientation.PORTRAIT,
            userName = userName
        )
    }

    @Test
    fun `toTypedStringWithoutErrors includes only correct characters and separators`() {
        val list = listOf(
            keystroke('h', 0, isCorrect = true),
            keystroke('x', 100, isCorrect = false),
            keystroke(' ', 200, isCorrect = false, isSeparator = true),
            keystroke('w', 300, isCorrect = true)
        )

        val result = list.toTypedStringWithoutErrors()

        assertThat(result).isEqualTo("h w")
    }

    @Test
    fun `activeTypingDurationMillis returns total time excluding long pauses`() {
        val list = listOf(
            keystroke('h', 0),
            keystroke('e', 1_000),
            keystroke('l', 2_000),
            keystroke('l', 32_000), // Long pause, excluded
            keystroke('o', 33_000)
        )

        val result = list.activeTypingDurationMillis()

        assertThat(result).isEqualTo(2_000 + 1_000) // 3 seconds = 3000ms
    }

    @Test
    fun `calculateCorrectWords counts exact matches only`() {
        val typed = "hello world"
        val reference = "hello world brave"

        val result = WordsPerMinuteCalculator.calculateCorrectWords(typed, reference)

        assertThat(result).isEqualTo(2) // "hello" and "world"
    }

    @Test
    fun `calculateWordsPerMinute returns correct value for valid input`() {
        val reference = "hello world"
        val list = listOf(
            keystroke('h', 0),
            keystroke('e', 500),
            keystroke('l', 1_000),
            keystroke('l', 1_500),
            keystroke('o', 2_000),
            keystroke(' ', 2_500, isSeparator = true),
            keystroke('w', 3_000),
            keystroke('o', 3_500),
            keystroke('r', 4_000),
            keystroke('l', 4_500),
            keystroke('d', 5_000)
        )

        val wpm = WordsPerMinuteCalculator.calculateWordsPerMinute(reference, list)

        // Total time = 5s = 0.0833 min, 2 correct words = ~24 WPM
        assertThat(wpm).isWithin(0.1).of(24.0)
    }

    @Test
    fun `calculateWordsPerMinute returns 0 when no active time`() {
        val result = WordsPerMinuteCalculator.calculateWordsPerMinute("hello", emptyList())

        assertThat(result).isEqualTo(0.0)
    }
}
