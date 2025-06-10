package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.R

@Composable
fun TrackingTextFieldLabel() {
    Text(text = stringResource(R.string.tracking_text_field_label))
}