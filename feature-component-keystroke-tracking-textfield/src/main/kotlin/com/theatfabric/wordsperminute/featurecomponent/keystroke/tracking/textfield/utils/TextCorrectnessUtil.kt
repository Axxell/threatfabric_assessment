package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.utils

object TextCorrectnessUtil {

    fun isLastCharacterCorrect(enteredText: String, referenceText: String): Boolean {
        if (
            enteredText.isEmpty()
            || referenceText.isEmpty()
            || enteredText.length > referenceText.length
        ) {
            return false
        }

        val lastEnteredChar = enteredText.last()
        val relatedCorrectChar = referenceText[enteredText.lastIndex]

        return lastEnteredChar == relatedCorrectChar
    }

    fun shouldAcceptTheNewText(typedText: String, referenceText: String): Boolean {
        return typedText.length <= referenceText.length
    }

}