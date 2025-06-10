package com.theatfabric.wordsperminute.domaindata.keystrokes.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.converters.KeystrokeDtoToModelMapper
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.converters.KeystrokeModelToDtoMapper
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.TypeSpeedSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.PhoneOrientationDto
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class TypeSpeedRepositoryImplTest {

    private val typeSpeedSource: TypeSpeedSource = mockk()
    private val dtoToModelMapper: KeystrokeDtoToModelMapper = mockk()
    private val modelToDtoMapper: KeystrokeModelToDtoMapper = mockk()

    private lateinit var repository: TypeSpeedRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = TypeSpeedRepositoryImpl(typeSpeedSource, dtoToModelMapper, modelToDtoMapper)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `saveKeystroke maps and passes keystroke dto to source`() = runTest {
        val keystroke = Keystroke(
            gameId = "game123",
            keyPressedMillis = 100L,
            keyReleasedMillis = 150L,
            keyCode = 65,
            isCorrect = true,
            isSeparator = false,
            phoneOrientation = PhoneOrientation.PORTRAIT,
            userName = "tester"
        )
        val dto = KeystrokeDto(
            gameId = "game123",
            keyPressedMillis = 100L,
            keyReleasedMillis = 150L,
            keyCode = 65,
            isCorrect = true,
            isSeparator = false,
            phoneOrientation = PhoneOrientationDto.PORTRAIT,
            userName = "tester"
        ).apply {
            id = 1
        }

        every { modelToDtoMapper(keystroke) } returns dto
        coEvery { typeSpeedSource.addKeystroke(dto) } just Runs

        repository.saveKeystroke(keystroke)

        coVerify { typeSpeedSource.addKeystroke(dto) }
    }

    @Test
    fun `getGameKeystrokes maps dto list to model list`() = runTest {
        val gameId = "game123"
        val dtoList = listOf(
            KeystrokeDto(
                gameId = "game123",
                keyPressedMillis = 100L,
                keyReleasedMillis = 150L,
                keyCode = 65,
                isCorrect = true,
                isSeparator = false,
                phoneOrientation = PhoneOrientationDto.PORTRAIT,
                userName = "tester"
            ).apply {
                id = 1
            }
        )
        val modelList = listOf(
            Keystroke(
                gameId = "game123",
                keyPressedMillis = 100L,
                keyReleasedMillis = 150L,
                keyCode = 65,
                isCorrect = true,
                isSeparator = false,
                phoneOrientation = PhoneOrientation.PORTRAIT,
                userName = "tester"
            )
        )

        coEvery { typeSpeedSource.getGameKeystrokes(gameId) } returns dtoList
        every { dtoToModelMapper(dtoList[0]) } returns modelList[0]

        val result = repository.getGameKeystrokes(gameId)
        assertThat(result).isEqualTo(modelList)
    }

    @Test
    fun `observeGameKeystrokes emits sorted mapped keystrokes`() = runTest {
        val gameId = "game123"
        val dto1 = KeystrokeDto(
            gameId = "game123",
            keyPressedMillis = 200L,
            keyReleasedMillis = 200L,
            keyCode = 95,
            isCorrect = true,
            isSeparator = false,
            phoneOrientation = PhoneOrientationDto.PORTRAIT,
            userName = "tester"
        ).apply {
            id = 2
        }
        val dto2 = KeystrokeDto(
            gameId = "game123",
            keyPressedMillis = 100L,
            keyReleasedMillis = 100L,
            keyCode = 65,
            isCorrect = true,
            isSeparator = false,
            phoneOrientation = PhoneOrientationDto.PORTRAIT,
            userName = "tester"
        ).apply {
            id = 1
        }
        val model1 = Keystroke(
            gameId = "game123",
            keyPressedMillis = 200L,
            keyReleasedMillis = 200L,
            keyCode = 95,
            isCorrect = true,
            isSeparator = false,
            phoneOrientation = PhoneOrientation.PORTRAIT,
            userName = "tester"
        )
        val model2 = Keystroke(
            gameId = "game123",
            keyPressedMillis = 100L,
            keyReleasedMillis = 100L,
            keyCode = 65,
            isCorrect = true,
            isSeparator = false,
            phoneOrientation = PhoneOrientation.PORTRAIT,
            userName = "tester"
        )

        val flow = flowOf(listOf(dto1, dto2))
        every { typeSpeedSource.observeGameKeystrokes(gameId) } returns flow
        every { dtoToModelMapper(dto1) } returns model1
        every { dtoToModelMapper(dto2) } returns model2

        repository.observeGameKeystrokes(gameId)
            .test {
                val result = awaitItem()
                assertThat(result).containsExactly(model2, model1).inOrder()
                cancelAndIgnoreRemainingEvents()
            }
    }

    @Test
    fun `deleteGameKeystrokes delegates to source`() = runTest {
        val gameId = "game123"
        coEvery { typeSpeedSource.deleteKeystrokesForGame(gameId) } just Runs

        repository.deleteGameKeystrokes(gameId)

        coVerify { typeSpeedSource.deleteKeystrokesForGame(gameId) }
    }

    @Test
    fun `deleteAll delegates to source`() = runTest {
        coEvery { typeSpeedSource.deleteAllKeystrokes() } just Runs

        repository.deleteAll()

        coVerify { typeSpeedSource.deleteAllKeystrokes() }
    }
}
