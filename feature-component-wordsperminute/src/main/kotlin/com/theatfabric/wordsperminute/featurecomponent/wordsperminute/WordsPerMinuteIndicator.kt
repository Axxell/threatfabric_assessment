package com.theatfabric.wordsperminute.featurecomponent.wordsperminute

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun WordsPerMinuteIndicator(
    modifier: Modifier = Modifier,
    viewModel: WordsPerMinuteIndicatorViewModel = hiltViewModel()
) {
    val wordsPerMinuteState by viewModel.gameIdToWordsPerMinuteStateFlow.collectAsStateWithLifecycle()

    Text(
        modifier = modifier,
        text = "WPM: ${"%.2f".format(wordsPerMinuteState.second)}"
    )
}