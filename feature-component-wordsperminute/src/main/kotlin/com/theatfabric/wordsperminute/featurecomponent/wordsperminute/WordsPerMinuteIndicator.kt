package com.theatfabric.wordsperminute.featurecomponent.wordsperminute

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WordsPerMinuteIndicator(
    wordsPerMinute: Double,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = "WPM: ${"%.2f".format(wordsPerMinute)}"
    )
}

@Preview(showBackground = true)
@Composable
fun WordsPerMinuteIndicatorPreview() {
    MaterialTheme {
        WordsPerMinuteIndicator(
            wordsPerMinute = 72.56
        )
    }
}