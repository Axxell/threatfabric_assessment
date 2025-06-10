package com.theatfabric.wordsperminute.feature.gamescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.SoftwareKeyboardTrackingTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val referenceTextState by viewModel.referenceTextStateFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("WPM will be here") })
        },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = referenceTextState,
                    modifier = Modifier.padding(bottom = 20.dp)
                        .fillMaxWidth()
                )
                SoftwareKeyboardTrackingTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    referenceText = referenceTextState.text,
                    onKeyLogged = { viewModel.saveKeystroke(it) }
                )
            }
        }
    )
}
