package com.theatfabric.wordsperminute.domaindata.keystrokes.data.converters

import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.PhoneOrientationDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import javax.inject.Inject

internal class KeystrokeDtoToModelMapper @Inject constructor() {
    operator fun invoke(dto: KeystrokeDto): Keystroke {
        return Keystroke(
            gameId = dto.gameId,
            keyPressedMillis = dto.keyPressedMillis,
            keyReleasedMillis = dto.keyReleasedMillis,
            keyCode = dto.keyCode,
            isCorrect = dto.isCorrect,
            isSeparator = dto.isSeparator,
            phoneOrientation = dto.phoneOrientation.toModel(),
            userName = dto.userName
        )
    }
}

private fun PhoneOrientationDto.toModel(): PhoneOrientation {
    return when (this) {
        PhoneOrientationDto.PORTRAIT -> PhoneOrientation.PORTRAIT
        PhoneOrientationDto.LANDSCAPE -> PhoneOrientation.LANDSCAPE
    }
}

