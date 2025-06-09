package com.theatfabric.wordsperminute.domaindata.keystrokes.di

import android.content.Context
import androidx.room.Room
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.TypeSpeedSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.TypeSpeedLocalSource
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dao.KeystrokeDao
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.database.TypeSpeedDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface TypeSpeedDomainModule {

    @Binds
    @Singleton
    fun bindTypeSpeedSource(
        typeSpeedLocalSource: TypeSpeedLocalSource
    ): TypeSpeedSource


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
    }

}