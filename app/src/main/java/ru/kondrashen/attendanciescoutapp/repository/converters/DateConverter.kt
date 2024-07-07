package ru.kondrashen.attendanciescoutapp.repository.converters

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun  dateToTimestamp(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun  timestampToDate(item: Long): Date {
        return Date(item)
    }
}