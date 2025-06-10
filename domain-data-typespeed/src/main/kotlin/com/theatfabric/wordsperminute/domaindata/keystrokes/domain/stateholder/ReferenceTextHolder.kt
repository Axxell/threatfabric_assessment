package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder

import android.util.Log
import javax.inject.Inject

class ReferenceTextHolder @Inject constructor() {

    private val referenceTextMap = mutableMapOf<String, String>()

    fun getReferenceText(gameId: String): String {
        return referenceTextMap[gameId] ?: startNewGame(gameId)
    }

    private fun startNewGame(gameId: String): String {
        val generatedText = generateReferenceTextForGameId(gameId)
        referenceTextMap[gameId] = generatedText
        return generatedText
    }

    private fun generateReferenceTextForGameId(gameId: String): String {
        Log.d(this::class.simpleName, "Reference text for gameId($gameId) generated.")
        return TEST_REFERENCE_TEXT
    }

    companion object {
        private const val TEST_REFERENCE_TEXT =
            "He thought he would light the fire when he got inside, and " +
                    "make himself some breakfast, just to pass away the time; " +
                    "but he did not seem able to handle anything from " +
                    "a scuttleful of coals to a"
    }
}