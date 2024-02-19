package ru.kondrashen.attendanciescoutapp.repository.data_class.relations

import androidx.room.Embedded
import androidx.room.Relation
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student

data class GroupWithStudents(
    @Embedded
    val group: Group,
    @Relation(
        parentColumn = "id",
        entityColumn = "groupId",
    )
    val student: List<Student>
)
