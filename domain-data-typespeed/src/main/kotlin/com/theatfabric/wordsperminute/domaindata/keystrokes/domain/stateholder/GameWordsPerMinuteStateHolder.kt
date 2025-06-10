package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder

import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.GameWordsPerMinuteState
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.util.WordsPerMinuteCalculator
import com.threatfabric.wordsperminute.foundation.coroutines.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameWordsPerMinuteStateHolder @Inject constructor(
    @AppScope private val appScope: CoroutineScope,
    private val gameKeystrokesStateFlowHolder: GameKeystrokesStateFlowHolder,
    private val referenceTextHolder: ReferenceTextHolder
) {

    private val mutableGameWordsPerMinuteStateFlow =
        MutableStateFlow(
            GameWordsPerMinuteState(
                gameId = null,
                wordsPerMinute = 0.0
            )
        )
    val gameWordsPerMinuteStateFlow = mutableGameWordsPerMinuteStateFlow.asStateFlow()

    init {
        appScope.launch {
            gameKeystrokesStateFlowHolder.gameKeystrokesStateFlow.collect { (gameId, keystrokes) ->
                mutableGameWordsPerMinuteStateFlow.emit(
                    GameWordsPerMinuteState(
                        gameId = gameId,
                        wordsPerMinute = WordsPerMinuteCalculator.calculateWordsPerMinute(
                            referenceText = referenceTextHolder.getReferenceText(gameId ?: ""),
                            keystrokes
                        )
                    )
                )
            }
        }
    }

}
