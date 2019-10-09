package com.mourad.rockpaperscissors.util

import androidx.room.TypeConverter
import com.mourad.rockpaperscissors.model.Move
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromInt(move: Int?): Move? {
        when(move) {
            Move.rock.image -> {
                return Move.rock
            }
            Move.paper.image -> {
                return Move.paper
            }
            Move.scissors.image -> {
                return Move.scissors
            }
        }
        return null
    }

    @TypeConverter
    fun moveToInt(move: Move?): Int? {
        return move?.image?.toInt()
    }
}