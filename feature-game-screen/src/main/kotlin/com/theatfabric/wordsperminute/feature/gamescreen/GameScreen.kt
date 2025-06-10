package com.theatfabric.wordsperminute.feature.gamescreen

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theatfabric.wordsperminute.feature.gamescreen.ui.GameScreenContent

@Composable
fun GameScreen(
    onGameFinished: () -> Unit,
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val referenceTextState by viewModel.referenceTextStateFlow.collectAsStateWithLifecycle()
    val gameWordsPerMinuteState by viewModel.wordsPerMinuteStateFlow.collectAsStateWithLifecycle()
    val gameIsFinished by viewModel.gameIsFinishedSharedFlow.collectAsStateWithLifecycle(false)

    GameScreenContent(
        referenceText = referenceTextState,
        wordsPerMinute = gameWordsPerMinuteState.wordsPerMinute,
        onKeyLogged = { viewModel.saveKeystroke(it) }
    )

    if (gameIsFinished) {
        AlertDialog(
            onDismissRequest = { viewModel.resetGameFinishedDialog() },
            title = { Text(stringResource(R.string.game_finished_title)) },
            text = {
                Text(
                    stringResource(
                        R.string.game_finished_text,
                        gameWordsPerMinuteState.wordsPerMinute
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetGameFinishedDialog()
                    onGameFinished()
                }) {
                    Text(stringResource(R.string.text_ok))
                }
            }
        )
    }
}
