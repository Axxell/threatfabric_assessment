package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.usecase

import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository.TypeSpeedRepository
import javax.inject.Inject

class DeleteGameKeystrokesUseCase @Inject internal constructor(
    private val repository: TypeSpeedRepository
) {
    suspend operator fun invoke(
        gameId: String
    ) = repository.deleteGameKeystrokes(gameId)
}