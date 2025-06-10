package com.theatfabric.wordsperminute.feature.gamescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.SoftwareKeyboardTrackingTextField
import com.theatfabric.wordsperminute.featurecomponent.wordsperminute.WordsPerMinuteIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    onGameFinished: () -> Unit,
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val referenceTextState by viewModel.referenceTextStateFlow.collectAsStateWithLifecycle()
    var showWpmDialog by remember { mutableStateOf(false) }

    val gameIdToWPM by viewModel.wordsPerMinuteStateFlow.collectAsStateWithLifecycle()
    val gameIsFinished by viewModel.gameIsFinishedSharedFlow.collectAsStateWithLifecycle(false)

    showWpmDialog = gameIsFinished

    Scaffold(
        topBar = {
            TopAppBar(title = {
                WordsPerMinuteIndicator()
            })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = referenceTextState,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )
                SoftwareKeyboardTrackingTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    gameId = gameIdToWPM.first ?: "",
                    onKeyLogged = { viewModel.saveKeystroke(it) }
                )
            }

            if (showWpmDialog) {
                AlertDialog(
                    onDismissRequest = { showWpmDialog = false },
                    title = { Text(stringResource(R.string.game_finished_title)) },
                    text = {
                        Text(
                            stringResource(R.string.game_finished_text, gameIdToWPM.second)
                        )
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            onGameFinished()
                            showWpmDialog = false
                        }) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    )
}
