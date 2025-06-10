package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.hardware

import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.type
import androidx.lifecycle.viewModelScope
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui.keyboard.BaseTrackingTextFieldViewModel
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.utils.TextCorrectnessUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HardwareKeyboardTrackingTextFieldViewModel @Inject constructor() : BaseTrackingTextFieldViewModel() {

    private val keyPressTimestamps = mutableMapOf<Int, Long>()

    fun processKeyEvent(keyEvent: KeyEvent): Boolean {
        val keyCode = keyEvent.nativeKeyEvent.keyCode
        val timestamp = System.currentTimeMillis()

        return when (keyEvent.type) {
            KeyEventType.KeyDown -> {
                if (keyCode != KeyEvent.KEYCODE_DEL) {
                    keyPressTimestamps[keyCode] = timestamp
                }
                true
            }

            KeyEventType.KeyUp -> {
                if (keyCode != KeyEvent.KEYCODE_DEL) {
                    val keyPressTime = keyPressTimestamps.remove(keyCode) ?: timestamp

                    val charTyped = keyEvent.nativeKeyEvent.unicodeChar.toChar()
                    mutableTextFieldValueState.value += charTyped

                    viewModelScope.launch {
                        mutableLoggedKeyEventFlow.emit(
                            LoggedKeyEvent(
                                keyChar = charTyped,
                                keyCode = charTyped.code,
                                keyPressedMillis = keyPressTime,
                                keyReleasedMillis = timestamp,
                                isCorrect = TextCorrectnessUtil.isLastCharacterCorrect(
                                    enteredText = mutableTextFieldValueState.value,
                                    referenceText = referenceText.value
                                )
                            )
                        )
                    }
                }
                true
            }

            else -> true
        }
    }

}