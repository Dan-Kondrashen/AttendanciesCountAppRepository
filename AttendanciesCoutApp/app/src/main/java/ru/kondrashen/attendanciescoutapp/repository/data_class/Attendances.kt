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
    var subjectId: Int,
    var studentId: Int,
    var userId: Int,
    var statys: String,
    var datetime: String
)
