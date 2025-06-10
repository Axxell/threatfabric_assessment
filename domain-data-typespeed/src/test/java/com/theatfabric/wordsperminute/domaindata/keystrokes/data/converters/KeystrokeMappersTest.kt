package com.theatfabric.wordsperminute.domaindata.keystrokes.data.converters

import com.google.common.truth.Truth.assertThat
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.PhoneOrientationDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import org.junit.jupiter.api.Test

class KeystrokeMappersTest {

    private val dtoToModel = KeystrokeDtoToModelMapper()
    private val modelToDto = KeystrokeModelToDtoMapper()

    @Test
    fun `dto is correctly mapped to model`() {
        val dto = KeystrokeDto(
            gameId = "game123",
            keyPressedMillis = 100L,
            keyReleasedMillis = 200L,
            keyCode = 65,
            isCorrect = true,
            isSeparator = false,
            phoneOrientation = PhoneOrientationDto.PORTRAIT,
            userName = "Alice"
        )

        val model = dtoToModel(dto)

        assertThat(model.gameId).isEqualTo(dto.gameId)
        assertThat(model.keyPressedMillis).isEqualTo(dto.keyPressedMillis)
        assertThat(model.keyReleasedMillis).isEqualTo(dto.keyReleasedMillis)
        assertThat(model.keyCode).isEqualTo(dto.keyCode)
        assertThat(model.isCorrect).isEqualTo(dto.isCorrect)
        assertThat(model.isSeparator).isEqualTo(dto.isSeparator)
        assertThat(model.phoneOrientation).isEqualTo(PhoneOrientation.PORTRAIT)
        assertThat(model.userName).isEqualTo(dto.userName)
    }

    @Test
    fun `model is correctly mapped to dto`() {
        val model = Keystroke(
            gameId = "game456",
            keyPressedMillis = 300L,
            keyReleasedMillis = 400L,
            keyCode = 66,
            isCorrect = false,
            isSeparator = true,
            phoneOrientation = PhoneOrientation.LANDSCAPE,
            userName = "Bob"
        )

        val dto = modelToDto(model)

        assertThat(dto.gameId).isEqualTo(model.gameId)
        assertThat(dto.keyPressedMillis).isEqualTo(model.keyPressedMillis)
        assertThat(dto.keyReleasedMillis).isEqualTo(model.keyReleasedMillis)
        assertThat(dto.keyCode).isEqualTo(model.keyCode)
        assertThat(dto.isCorrect).isEqualTo(model.isCorrect)
        assertThat(dto.isSeparator).isEqualTo(model.isSeparator)
        assertThat(dto.phoneOrientation).isEqualTo(PhoneOrientationDto.LANDSCAPE)
        assertThat(dto.userName).isEqualTo(model.userName)
    }
}
