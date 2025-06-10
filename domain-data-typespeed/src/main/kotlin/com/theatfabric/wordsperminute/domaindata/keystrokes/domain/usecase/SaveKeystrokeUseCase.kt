package com.theatfabric.wordsperminute.domaindata.keystrokes.domain.usecase

import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository.TypeSpeedRepository
import javax.inject.Inject

class SaveKeystrokeUseCase @Inject internal constructor(
    private val repository: TypeSpeedRepository
) {
    suspend operator fun invoke(
        keystroke: Keystroke
    ) = repository.saveKeystroke(keystroke)
}