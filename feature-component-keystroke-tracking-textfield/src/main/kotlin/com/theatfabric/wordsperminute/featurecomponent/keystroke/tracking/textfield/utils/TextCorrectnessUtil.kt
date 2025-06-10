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

    fun shouldAcceptTheNewText(
        newText: String,
        oldText: String,
        referenceText: String
    ): Boolean {
        return newText.length <= referenceText.length
                && newText.length == oldText.length + 1
    }

}