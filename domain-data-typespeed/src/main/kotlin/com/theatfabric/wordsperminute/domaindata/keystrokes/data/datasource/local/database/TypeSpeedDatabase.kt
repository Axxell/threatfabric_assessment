package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dao.KeystrokeDao
import com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto.KeystrokeDto

@Database(entities = [KeystrokeDto::class], version = 1)
internal abstract class TypeSpeedDatabase : RoomDatabase() {
    abstract fun keystrokeDao(): KeystrokeDao
}