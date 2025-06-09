package com.theatfabric.wordsperminute.feature.initialscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitialScreen(
    onNavigateToGameScreen: (String, String) -> Unit
) {
    val viewModel = hiltViewModel<InitialScreenViewModel>()

    LaunchedEffect(Unit) {
        viewModel.navigationFlow.collect { userName ->
            onNavigateToGameScreen(
                userName,
                viewModel.gameId
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TypeSpeed Game") })
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    modifier = Modifier.padding(16.dp)
                        .fillMaxWidth(),
                    value = viewModel.userName,
                    onValueChange = { viewModel.userName = it },
                    label = { Text("UserName:") }
                )
                Button(
                    modifier = Modifier.padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onClick = { viewModel.onStartClicked() }
                ) {
                    Text("Start game")
                }

            }
        }
    )
}
