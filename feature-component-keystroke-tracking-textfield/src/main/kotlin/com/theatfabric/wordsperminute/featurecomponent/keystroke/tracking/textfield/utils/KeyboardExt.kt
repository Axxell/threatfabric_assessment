package com.theatfabric.wordsperminute.featurecomponent.keystroke.tracking.textfield.utils

import android.content.Context
import android.content.res.Configuration

fun Context.isHardwareKeyboardConnected(): Boolean {
    val config = resources.configuration
    return config.keyboard != Configuration.KEYBOARD_NOKEYS
}