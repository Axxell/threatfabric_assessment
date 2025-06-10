package com.theatfabric.wordsperminute.domaindata.keystrokes.data.datasource.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keystrokes")
internal data class KeystrokeDto(
    @ColumnInfo(name = "game_id") val gameId: String,
    @ColumnInfo(name = "key_pressed_millis") val keyPressedMillis: Long,
    @ColumnInfo(name = "key_released_millis") val keyReleasedMillis: Long,
    @ColumnInfo(name = "key_code") val keyCode: Int,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
    @ColumnInfo(name = "phone_orientation") val phoneOrientation: PhoneOrientationDto,
    @ColumnInfo(name = "user_name") val userName: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}

enum class PhoneOrientationDto {
    PORTRAIT,
    LANDSCAPE
}