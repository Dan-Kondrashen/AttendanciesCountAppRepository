package ru.kondrashen.attendanciescoutapp.repository.data_class.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student

data class StudentInGroup(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "groupId",
        entityColumn = "id",
    )
    val group: Group

)
