package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.software

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.TrackingTextFieldLabel

@Composable
fun SoftwareKeyboardTrackingTextField(
    modifier: Modifier = Modifier,
    referenceText: String,
    onKeyLogged: (LoggedKeyEvent) -> Unit
) {
    val viewModel = hiltViewModel<SoftwareKeyboardTrackingTextFieldViewModel>()

    LaunchedEffect(Unit) {
        viewModel.referenceText.value = referenceText

        viewModel.loggedKeyEventFlow.collect {
            onKeyLogged(it)
        }
    }

    val textFieldValueState by viewModel.textFieldValueState.collectAsStateWithLifecycle()

    TextField(
        value = textFieldValueState,
        label = { TrackingTextFieldLabel() },
        onValueChange = {

        },
        modifier = modifier
    )
}