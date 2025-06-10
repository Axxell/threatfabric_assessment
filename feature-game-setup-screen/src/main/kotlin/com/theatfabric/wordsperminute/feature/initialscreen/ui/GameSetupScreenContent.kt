package com.theatfabric.wordsperminute.feature.initialscreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theatfabric.wordsperminute.feature.initialscreen.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSetupScreenContent(
    userName: String,
    onUserNameChange: (String) -> Unit,
    onStartClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.game_setup_screen_title)) }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    value = userName,
                    onValueChange = onUserNameChange,
                    label = { Text(stringResource(R.string.text_field_username_label)) }
                )
                Button(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    onClick = onStartClicked
                ) {
                    Text(stringResource(R.string.button_start_game_title))
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameSetupScreenPreview() {
    MaterialTheme {
        GameSetupScreenContent(
            userName = "PreviewUser",
            onUserNameChange = {},
            onStartClicked = {}
        )
    }
}

