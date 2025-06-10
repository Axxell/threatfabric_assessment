package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util

import com.google.common.truth.Truth.assertThat
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util.WordsPerMinuteCalculator.activeTypingDurationMillis
import org.junit.jupiter.api.Test

class WordsPerMinuteCalculatorTest {

    private fun createKeystroke(
        keyCode: Int,
        isCorrect: Boolean,
        millis: Long
    ): Keystroke {
        val char = keyCode.toChar()
        return Keystroke(
            gameId = "game123",
            keyPressedMillis = millis,
            keyReleasedMillis = millis + 50,
            keyCode = keyCode,
            isCorrect = isCorrect,
            phoneOrientation = PhoneOrientation.PORTRAIT,
            userName = "test"
        )
    }

    @Test
    fun `single correct word is counted`() {
        val reference = "hello "
        val keystrokes = listOf(
            createKeystroke('h'.code, true, 0),
            createKeystroke('e'.code, true, 100),
            createKeystroke('l'.code, true, 200),
            createKeystroke('l'.code, true, 300),
            createKeystroke('o'.code, true, 400),
            createKeystroke(' '.code, true, 500)
        )

        val result = WordsPerMinuteCalculator.calculateCorrectWords(keystrokes, reference)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `word with one incorrect character is not counted`() {
        val reference = "hello "
        val keystrokes = listOf(
            createKeystroke('h'.code, true, 0),
            createKeystroke('e'.code, true, 100),
            createKeystroke('x'.code, false, 200),
            createKeystroke('l'.code, true, 300),
            createKeystroke('o'.code, true, 400),
            createKeystroke(' '.code, true, 500)
        )

        val result = WordsPerMinuteCalculator.calculateCorrectWords(keystrokes, reference)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `multiple words with some incorrect are counted properly`() {
        val reference = "hello world test "
        val keystrokes = listOf(
            // "hello"
            createKeystroke('h'.code, true, 0),
            createKeystroke('e'.code, true, 50),
            createKeystroke('l'.code, true, 100),
            createKeystroke('l'.code, true, 150),
            createKeystroke('o'.code, true, 200),
            createKeystroke(' '.code, true, 250),

            // "world" (incorrect)
            createKeystroke('w'.code, true, 400),
            createKeystroke('o'.code, true, 450),
            createKeystroke('x'.code, false, 500), // wrong
            createKeystroke('l'.code, true, 550),
            createKeystroke('d'.code, true, 600),
            createKeystroke(' '.code, true, 650),

            // "test"
            createKeystroke('t'.code, true, 800),
            createKeystroke('e'.code, true, 850),
            createKeystroke('s'.code, true, 900),
            createKeystroke('t'.code, true, 950),
            createKeystroke(' '.code, true, 1000)
        )

        val result = WordsPerMinuteCalculator.calculateCorrectWords(keystrokes, reference)
        assertThat(result).isEqualTo(2) // "hello" and "test"
    }

    @Test
    fun `active typing duration excludes large pauses`() {
        val keystrokes = listOf(
            createKeystroke('a'.code, true, 0),
            createKeystroke('b'.code, true, 1000),
            createKeystroke('c'.code, true, 3000),
            createKeystroke('d'.code, true, 20000), // large pause (17s)
            createKeystroke('e'.code, true, 20100)
        )

        val duration = keystrokes.activeTypingDurationMillis()
        assertThat(duration).isEqualTo(3100) // 1s + 2s, no 17s pause included
    }

    @Test
    fun `full WPM calculation with correct timing`() {
        val reference = "hi there "
        val keystrokes = listOf(
            createKeystroke('h'.code, true, 0),
            createKeystroke('i'.code, true, 500),
            createKeystroke(' '.code, true, 1000),
            createKeystroke('t'.code, true, 1500),
            createKeystroke('h'.code, true, 2000),
            createKeystroke('e'.code, true, 2500),
            createKeystroke('r'.code, true, 3000),
            createKeystroke('e'.code, true, 3500),
            createKeystroke(' '.code, true, 4000)
        )

        val wpm = WordsPerMinuteCalculator.calculateWordsPerMinute(reference, keystrokes)

        // 2 words over ~4 seconds = ~30 WPM
        assertThat(wpm).isWithin(1.0).of(30.0)
    }

}
