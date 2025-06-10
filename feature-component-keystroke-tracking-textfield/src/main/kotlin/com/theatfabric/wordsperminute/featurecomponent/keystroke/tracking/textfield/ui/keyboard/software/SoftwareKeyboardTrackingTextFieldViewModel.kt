package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.software

import androidx.lifecycle.viewModelScope
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.BaseTrackingTextFieldViewModel
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.utils.TextCorrectnessUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SoftwareKeyboardTrackingTextFieldViewModel @Inject constructor() :
    BaseTrackingTextFieldViewModel() {

    fun processEnteredText(enteredText: String) {
        val currentTime = System.currentTimeMillis()

        val oldText = mutableTextFieldValueState.value

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
        }
    }

}