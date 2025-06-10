package com.theatfabric.wordsperminute.domaindata.keystrokes.data.converters

import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.PhoneOrientationDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import javax.inject.Inject

internal class KeystrokeModelToDtoMapper @Inject constructor() {
    operator fun invoke(keystroke: Keystroke): KeystrokeDto = with (keystroke) {
        return KeystrokeDto(
            gameId = gameId,
            keyPressedMillis = keyPressedMillis,
            keyReleasedMillis = keyReleasedMillis,
            keyCode = keyCode,
            isCorrect = isCorrect,
            phoneOrientation = phoneOrientation.toDto(),
            userName = userName
        )
    }
}

private fun PhoneOrientation.toDto(): PhoneOrientationDto {
    return when (this) {
        PhoneOrientation.PORTRAIT -> PhoneOrientationDto.PORTRAIT
        PhoneOrientation.LANDSCAPE -> PhoneOrientationDto.LANDSCAPE
    }
}