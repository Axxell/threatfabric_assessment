package com.theatfabric.wordsperminute.feature.gamescreen.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.KeyboardTrackingTextField
import com.theatfabric.wordsperminute.featurecomponent.wordsperminute.WordsPerMinuteIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreenContent(
    referenceText: AnnotatedString,
    wordsPerMinute: Double,
    onKeyLogged: (LoggedKeyEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                WordsPerMinuteIndicator(wordsPerMinute =  wordsPerMinute)
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
                    text = referenceText,
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                )
                KeyboardTrackingTextField(
                    modifier = Modifier.fillMaxWidth(),
                    gameId = "previewGameId",
                    onKeyLogged = onKeyLogged
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GameScreenContentPreview() {
    MaterialTheme {
        GameScreenContent(
            referenceText = AnnotatedString("The quick brown fox jumps over the lazy dog."),
            wordsPerMinute = 72.0,
            onKeyLogged = {}
        )
    }
}
