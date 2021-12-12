package com.example.rxjava4.local.entity

import androidx.room.TypeConverter
import java.util.*

open class RoomDateConverters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }


}