package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

internal open class BaseTrackingTextFieldViewModel : ViewModel() {

    internal val mutableLoggedKeyEventFlow = MutableSharedFlow<LoggedKeyEvent>()
    val loggedKeyEventFlow = mutableLoggedKeyEventFlow.asSharedFlow()

    internal  val mutableTextFieldValueState = MutableStateFlow("")
    val textFieldValueState = mutableTextFieldValueState.asStateFlow()

    var referenceText = mutableStateOf("")

}