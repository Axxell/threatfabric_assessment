package com.theatfabric.wordsperminute.feature.gamescreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val userName: String = checkNotNull(savedStateHandle["userName"])
    val gameId: String = checkNotNull(savedStateHandle["gameId"])

}