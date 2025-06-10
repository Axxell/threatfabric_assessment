package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder

import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository.TypeSpeedRepository
import com.threatfabric.wordsperminute.foundation.coroutines.AppScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameKeystrokesStateFlowHolder @Inject constructor(
    @AppScope private val appScope: CoroutineScope,
    private val typeSpeedRepository: TypeSpeedRepository
) {

    private val mutableGameIdToKeystrokesStateFlow =
        MutableStateFlow<Pair<String?, List<Keystroke>>>(
            null to emptyList()
        )
    val gameIdToKeystrokesStateFlow = mutableGameIdToKeystrokesStateFlow.asStateFlow()

    private var currentJob: Job? = null
    private var currentGameId: String? = null

    fun startNewGame(gameId: String) {
        if (gameId.isNotEmpty() && currentGameId != gameId) {
            currentGameId = gameId
            currentJob?.cancel()
            currentJob = appScope.launch {
                typeSpeedRepository.observeGameKeystrokes(gameId).collect {
                    mutableGameIdToKeystrokesStateFlow.emit(gameId to it)
                }
            }
        }
    }
}