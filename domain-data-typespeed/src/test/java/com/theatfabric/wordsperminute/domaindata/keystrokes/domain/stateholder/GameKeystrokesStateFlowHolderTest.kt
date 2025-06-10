package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder

import com.google.common.truth.Truth.assertThat
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.PhoneOrientation
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository.TypeSpeedRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class GameKeystrokesStateFlowHolderTest {

    private val repository: TypeSpeedRepository = mockk()

    private lateinit var testScope: TestScope
    private lateinit var flowHolder: GameKeystrokesStateFlowHolder

    private val gameId = "game123"

    private val keystrokeList = listOf(
        Keystroke(
            gameId = gameId,
            keyPressedMillis = 100,
            keyReleasedMillis = 200,
            keyCode = 65,
            isCorrect = true,
            phoneOrientation = PhoneOrientation.PORTRAIT,
            userName = "tester"
        )
    )

    @BeforeEach
    fun setup() {
        testScope = TestScope()
        flowHolder = GameKeystrokesStateFlowHolder(testScope, repository)
    }

    @AfterEach
    fun tearDown() {
        testScope.cancel()
    }

    @Test
    fun `startNewGame collects and emits keystrokes`() = testScope.runTest {
        val keystrokeFlow = flowOf(keystrokeList)
        every { repository.observeGameKeystrokes(gameId) } returns keystrokeFlow

        flowHolder.startNewGame(gameId)

        val result = flowHolder.gameKeystrokesStateFlow.first {
            it.gameId == gameId && it.keystrokes.isNotEmpty()
        }

        assertThat(result.gameId).isEqualTo(gameId)
        assertThat(result.keystrokes).containsExactlyElementsIn(keystrokeList)
    }
}
