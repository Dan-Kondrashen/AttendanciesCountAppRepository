package ru.kondrashen.attendanciescoutapp.repository.data_class.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.kondrashen.attendanciescoutapp.repository.data_class.Attendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student

data class StudentsWithAttendancies(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "id",
        entityColumn = "studentId",
    )
    val attendances: List<Attendances>
)
