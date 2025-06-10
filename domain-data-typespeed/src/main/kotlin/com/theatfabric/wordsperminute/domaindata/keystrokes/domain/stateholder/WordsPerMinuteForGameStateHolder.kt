package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder

import androidx.annotation.VisibleForTesting
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.foundation.strings.splitIntoWords
import com.threatfabric.wordsperminute.foundation.coroutines.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WordsPerMinuteForGameStateHolder @Inject constructor(
    @AppScope private val appScope: CoroutineScope,
    private val gameKeystrokesStateFlowHolder: GameKeystrokesStateFlowHolder,
    private val referenceTextHolder: ReferenceTextHolder
) {

    private val mutableGameIdToWordsPerMinuteStateFlow =
        MutableStateFlow<Pair<String?, Double>>(
            null to 0.0
        )
    val gameIdToWordsPerMinuteStateFlow = mutableGameIdToWordsPerMinuteStateFlow.asStateFlow()

    init {
        appScope.launch {
            gameKeystrokesStateFlowHolder.gameIdToKeystrokesStateFlow.collect { (gameId, keystrokes) ->
                mutableGameIdToWordsPerMinuteStateFlow.emit(gameId to calculateWordsPerMinute(gameId, keystrokes))
            }
        }
    }

    @VisibleForTesting
    fun calculateWordsPerMinute(gameId: String?, keystrokes: List<Keystroke>): Double {
        val correctWords = calculateCorrectWords(
            typedText = keystrokes.toTypedStringWithoutErrors(),
            referenceText = referenceTextHolder.getReferenceText(gameId ?: "")
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

    companion object {
        private const val PAUSE_THRESHOLD_MILLIS = 30_000L
        private const val ONE_MINUTE_MILLIS = 60_000.0
    }

}