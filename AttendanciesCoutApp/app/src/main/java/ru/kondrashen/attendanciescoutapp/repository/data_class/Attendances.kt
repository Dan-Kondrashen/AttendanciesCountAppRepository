package ru.kondrashen.attendanciescoutapp.repository.data_class

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.kondrashen.attendanciescoutapp.repository.converters.DateConverter
import java.util.Date


@Entity("attendance_table")
data class Attendances(
    @PrimaryKey
    var id: Int,
    var subId: Int,
    var studId: Int,
    var userId: Int,
    var status: String,
    @TypeConverters(DateConverter::class)
    var date: Date
)
