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
fun SoftwareKeyboardTrackingTextField(
    modifier: Modifier = Modifier,
    referenceText: String,
    viewModel: SoftwareKeyboardTrackingTextFieldViewModel = hiltViewModel(),
    onKeyLogged: (LoggedKeyEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.referenceText.value = referenceText

        viewModel.loggedKeyEventFlow.collect {
            onKeyLogged(it)
        }
    }

    val textFieldValueState by viewModel.textFieldValueState.collectAsStateWithLifecycle()

    TextField(
        value = textFieldValueState,
        label = { Text(text = stringResource(R.string.tracking_text_field_label)) },
        onValueChange = {
            viewModel.processEnteredText(it.text)
        },
        modifier = modifier
    )
}