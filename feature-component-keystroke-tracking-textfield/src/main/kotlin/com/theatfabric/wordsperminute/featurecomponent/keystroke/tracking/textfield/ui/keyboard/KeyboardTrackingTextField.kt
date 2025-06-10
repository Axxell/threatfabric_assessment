package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard

import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.R
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent

@Composable
fun KeyboardTrackingTextField(
    modifier: Modifier = Modifier,
    viewModel: KeyboardTrackingTextFieldViewModel = hiltViewModel(),
    gameId: String,
    onKeyLogged: (LoggedKeyEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.gameId = gameId

        viewModel.loggedKeyEventFlow.collect {
            onKeyLogged(it)
        }
    }

    val textFieldValueState by viewModel.textFieldValueState.collectAsStateWithLifecycle()

    /*
     It's still possible to paste into the TextField but only 1 char.
     And it'll be processed normally so I see no issue here.
     */
    TextField(
        value = textFieldValueState,
        label = { Text(text = stringResource(R.string.tracking_text_field_label)) },
        onValueChange = {
            viewModel.processEnteredText(it.text)
        },
        modifier = modifier
    )
}