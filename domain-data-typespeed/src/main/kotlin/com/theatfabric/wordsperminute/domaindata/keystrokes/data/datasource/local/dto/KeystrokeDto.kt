package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keystrokes")
internal data class KeystrokeDto(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "game_id") val gameId: String,
    @ColumnInfo(name = "key_pressed") val keyPressed: Long,
    @ColumnInfo(name = "key_released") val keyReleased: Long,
    @ColumnInfo(name = "key_code") val keyCode: Int,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
    @ColumnInfo(name = "phone_orientation") val phoneOrientation: PhoneOrientationDto,
    @ColumnInfo(name = "user_name") val userName: String
)

enum class PhoneOrientationDto {
    PORTRAIT,
    LANDSCAPE
}