package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder.ReferenceTextHolder
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.utils.TextCorrectnessUtil
import com.theatfabric.wordsperminute.foundation.strings.isSeparator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class KeyboardTrackingTextFieldViewModel @Inject constructor(
    private val referenceTextHolder: ReferenceTextHolder
) : ViewModel() {

    private val mutableLoggedKeyEventFlow = MutableSharedFlow<LoggedKeyEvent>()
    val loggedKeyEventFlow = mutableLoggedKeyEventFlow.asSharedFlow()

    private val mutableTextFieldValueState = MutableStateFlow(TextFieldValue(""))
    val textFieldValueState = mutableTextFieldValueState.asStateFlow()

    var gameId by mutableStateOf("")

    private val referenceText by lazy {
        referenceTextHolder.getReferenceText(gameId)
    }

    fun processEnteredText(enteredText: String) {
        val currentTime = System.currentTimeMillis()

        val oldText = mutableTextFieldValueState.value.text

        if (TextCorrectnessUtil.shouldAcceptTheNewText(enteredText, oldText, referenceText)) {
            val typedChar = enteredText.last()
            val referenceChar = referenceText[enteredText.lastIndex]
            val isCorrect = TextCorrectnessUtil.isLastCharacterCorrect(
                enteredText,
                referenceText
            )

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