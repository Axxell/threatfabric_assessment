package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.utils.TextCorrectnessUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoftwareKeyboardTrackingTextFieldViewModel @Inject constructor() : ViewModel() {

    private val mutableLoggedKeyEventFlow = MutableSharedFlow<LoggedKeyEvent>()
    val loggedKeyEventFlow = mutableLoggedKeyEventFlow.asSharedFlow()

    private  val mutableTextFieldValueState = MutableStateFlow(TextFieldValue(""))
    val textFieldValueState = mutableTextFieldValueState.asStateFlow()

    var referenceText = mutableStateOf("")

    fun processEnteredText(enteredText: String) {
        val currentTime = System.currentTimeMillis()

        val oldText = mutableTextFieldValueState.value.text

        if (TextCorrectnessUtil.shouldAcceptTheNewText(enteredText, oldText)) {
            val typedChar = enteredText.last()
            val isCorrect =
                TextCorrectnessUtil.isLastCharacterCorrect(enteredText, referenceText.value)

            viewModelScope.launch {
                mutableLoggedKeyEventFlow.emit(
                    LoggedKeyEvent(
                        keyChar = typedChar,
                        keyCode = typedChar.code,
                        keyPressedMillis = currentTime,
                        keyReleasedMillis = currentTime,
                        isCorrect = isCorrect
                    )
                )
            }

            mutableTextFieldValueState.value = TextFieldValue(
                text = enteredText,
                selection = TextRange(enteredText.length)
            )
        }
    }

}