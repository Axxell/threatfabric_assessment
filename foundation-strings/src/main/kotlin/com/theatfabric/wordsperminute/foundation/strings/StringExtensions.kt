package com.theatfabric.wordsperminute.foundation.strings

private val WORD_SEPARATOR_REGEX = Regex("[\\s.,;:!?()\\[\\]{}\"'`]+")

fun String.splitIntoWords(): List<String> = trim().split(WORD_SEPARATOR_REGEX).filter {
    it.isNotEmpty()
}

fun Char.isSeparator(): Boolean = toString().matches(WORD_SEPARATOR_REGEX)