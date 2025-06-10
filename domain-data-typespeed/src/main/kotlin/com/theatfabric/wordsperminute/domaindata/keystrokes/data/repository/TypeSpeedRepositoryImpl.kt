package com.theatfabric.wordsperminute.domaindata.keystrokes.data.repository

import com.theatfabric.wordsperminute.domaindata.keystrokes.data.converters.KeystrokeDtoToModelMapper
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.converters.KeystrokeModelToDtoMapper
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.TypeSpeedSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.model.Keystroke
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository.TypeSpeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class TypeSpeedRepositoryImpl @Inject constructor(
    private val typeSpeedSource: TypeSpeedSource,
    private val keystrokeDtoToModelMapper: KeystrokeDtoToModelMapper,
    private val keystrokeModelToDtoMapper: KeystrokeModelToDtoMapper
) : TypeSpeedRepository {

    override suspend fun saveKeystroke(keystroke: Keystroke) {
        typeSpeedSource.addKeystroke(keystrokeModelToDtoMapper(keystroke))
    }

    override suspend fun getGameKeystrokes(gameId: String): List<Keystroke> {
        return typeSpeedSource.getGameKeystrokes(gameId)
            .map { keystrokeDtoToModelMapper(it) }
    }

    override fun observeGameKeystrokes(gameId: String): Flow<List<Keystroke>> {
        return typeSpeedSource.observeGameKeystrokes(gameId)
            .map { keystrokes ->
                keystrokes.map { keystrokeDtoToModelMapper(it) }
            }
    }

    override suspend fun deleteGameKeystrokes(gameId: String) {
        typeSpeedSource.deleteKeystrokesForGame(gameId)
    }

    override suspend fun deleteAll() {
        typeSpeedSource.deleteAllKeystrokes()
    }
}