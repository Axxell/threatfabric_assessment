package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.hardware.HardwareKeyboardTrackingTextField
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.software.SoftwareKeyboardTrackingTextField
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.utils.isHardwareKeyboardConnected

@Composable
fun AdaptiveKeyboardTrackingTextField(
    modifier: Modifier = Modifier,
    referenceText: String,
    onKeyLogged: (LoggedKeyEvent) -> Unit
) {
    val context = LocalContext.current
    val hardwareKeyboard = remember { context.isHardwareKeyboardConnected() }

    if (hardwareKeyboard) {
        HardwareKeyboardTrackingTextField(
            modifier = modifier,
            referenceText = referenceText,
            onKeyLogged = onKeyLogged
        )
    } else {
        SoftwareKeyboardTrackingTextField(
            modifier = modifier,
            referenceText = referenceText,
            onKeyLogged = onKeyLogged
        )
    }
}