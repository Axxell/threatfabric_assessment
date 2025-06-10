package com.theatfabric.wordsperminute.featurecomponent.wordsperminute

import androidx.lifecycle.ViewModel
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder.WordsPerMinuteForGameStateHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordsPerMinuteIndicatorViewModel @Inject constructor(
    wordsPerMinuteForGameStateHolder: WordsPerMinuteForGameStateHolder
) : ViewModel() {

    val gameIdToWordsPerMinuteStateFlow = wordsPerMinuteForGameStateHolder.gameIdToWordsPerMinuteStateFlow
}