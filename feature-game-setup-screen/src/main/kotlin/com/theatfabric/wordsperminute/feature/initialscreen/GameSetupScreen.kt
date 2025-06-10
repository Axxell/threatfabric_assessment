package com.theatfabric.wordsperminute.feature.initialscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.theatfabric.wordsperminute.feature.initialscreen.ui.GameSetupScreenContent

@Composable
fun GameSetupScreen(
    onNavigateToGameScreen: (String, String) -> Unit,
    viewModel: GameSetupScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.navigationFlow.collect { userName ->
            onNavigateToGameScreen(userName, viewModel.gameId)
        }
    }

    GameSetupScreenContent(
        userName = viewModel.userName,
        onUserNameChange = { viewModel.userName = it },
        onStartClicked = { viewModel.onStartClicked() }
    )
}
