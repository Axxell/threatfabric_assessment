package com.theatfabric.wordsperminute.domaindata.keystrokes.di

import android.content.Context
import androidx.room.Room
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.TypeSpeedSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.TypeSpeedLocalSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dao.KeystrokeDao
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.database.TypeSpeedDatabase
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.repository.TypeSpeedRepositoryImpl
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.repository.TypeSpeedRepository
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder.GameKeystrokesStateFlowHolder
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder.ReferenceTextHolder
import com.theatfabric.wordsperminute.domaindata.keystrokes.domain.stateholder.WordsPerMinuteForGameStateHolder
import com.threatfabric.wordsperminute.foundation.coroutines.AppScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface TypeSpeedDomainModule {

    @Binds
    @Singleton
    fun bindTypeSpeedSource(
        typeSpeedLocalSource: TypeSpeedLocalSource
    ): TypeSpeedSource

    @Binds
    @Singleton
    fun bindTypeSpeedRepository(
        typeSpeedRepositoryImpl: TypeSpeedRepositoryImpl
    ): TypeSpeedRepository

    companion object {

        @Provides
        @Singleton
        fun provideTypeSpeedDatabase(
            @ApplicationContext context: Context
        ): TypeSpeedDatabase {
            return Room.databaseBuilder(
                context,
                TypeSpeedDatabase::class.java,
                "typespeed_database"
            ).build()
        }

        @Provides
        @Singleton
        fun provideKeystrokeDao(
            database: TypeSpeedDatabase
        ): KeystrokeDao {
            return database.keystrokeDao()
        }

        @Provides
        @Singleton
        fun provideGameKeystrokesSharedFlowHolder(
            @AppScope appScope: CoroutineScope,
            typeSpeedRepository: TypeSpeedRepository
        ): GameKeystrokesStateFlowHolder {
            return GameKeystrokesStateFlowHolder(appScope, typeSpeedRepository)
        }

        @Provides
        @Singleton
        fun provideWordsPerMinuteForGameStateHolder(
            @AppScope appScope: CoroutineScope,
            gameKeystrokesStateFlowHolder: GameKeystrokesStateFlowHolder,
            referenceTextHolder: ReferenceTextHolder
        ): WordsPerMinuteForGameStateHolder {
            return WordsPerMinuteForGameStateHolder(appScope, gameKeystrokesStateFlowHolder, referenceTextHolder)
        }

        @Provides
        @Singleton
        fun provideReferenceTextHolder(): ReferenceTextHolder {
            return ReferenceTextHolder()
        }
    }

}