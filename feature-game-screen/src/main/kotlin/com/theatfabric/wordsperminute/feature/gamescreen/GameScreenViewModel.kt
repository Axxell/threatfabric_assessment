package com.theatfabric.wordsperminute.feature.gamescreen

import android.content.Context
import android.content.res.Configuration
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.usecase.ObserveGameKeystrokesUseCase
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.usecase.SaveKeystrokeUseCase
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.model.LoggedKeyEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle,
    private val saveKeystrokeUseCase: SaveKeystrokeUseCase,
    private val observeGameKeystrokesUseCase: ObserveGameKeystrokesUseCase
) : ViewModel() {

    private val userName: String = checkNotNull(savedStateHandle["userName"])
    private val gameId: String = checkNotNull(savedStateHandle["gameId"])
    private val referenceText = context.getString(R.string.reference_text)

    private val mutableReferenceTextStateFlow = MutableStateFlow(
        AnnotatedString(
            text = referenceText
        )
    )
    val referenceTextStateFlow = mutableReferenceTextStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            observeGameKeystrokesUseCase(gameId).collect {
                processKeystrokes(keystrokes = it)
            }
        }
    }

    private fun processKeystrokes(keystrokes: List<Keystroke>) {
        val paragraph = referenceTextStateFlow.value.text
        val annotatedString = buildAnnotatedString {
            for (i in paragraph.indices) {
                val paragraphChar = paragraph[i]
                val isTyped = i < keystrokes.size
                val isCorrect = if (isTyped) keystrokes[i].isCorrect else null

                val color = when {
                    !isTyped -> Color.Unspecified
                    isCorrect == true -> Color.Green
                    else -> Color.Red
                }

                withStyle(SpanStyle(color = color)) {
                    append(paragraphChar)
                }
            }
        }
        mutableReferenceTextStateFlow.value = annotatedString
    }

    fun saveKeystroke(loggedKeyEvent: LoggedKeyEvent) {
        viewModelScope.launch {
            saveKeystrokeUseCase(
                Keystroke(
                    gameId = gameId,
                    keyPressedMillis = loggedKeyEvent.keyPressedMillis,
                    keyReleasedMillis = loggedKeyEvent.keyReleasedMillis,
                    keyCode = loggedKeyEvent.keyCode,
                    isCorrect = loggedKeyEvent.isCorrect,
                    phoneOrientation = context.phoneOrientation(),
                    userName = userName
                )
            )
        }
    }

    private fun Context.phoneOrientation(): PhoneOrientation {
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> PhoneOrientation.PORTRAIT
            Configuration.ORIENTATION_LANDSCAPE -> PhoneOrientation.LANDSCAPE
            else -> PhoneOrientation.PORTRAIT
        }
    }
}

