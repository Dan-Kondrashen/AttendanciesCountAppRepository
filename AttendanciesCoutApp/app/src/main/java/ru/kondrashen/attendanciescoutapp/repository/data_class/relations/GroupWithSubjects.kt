package ru.kondrashen.attendanciescoutapp.repository.data_class.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Subject

data class GroupWithSubjects(
    @Embedded val group: Group,
    @Relation(parentColumn = "id",
        entityColumn = "subjectId",
        associateBy = Junction(SubjectToGroupCrossRef::class)
    )
    val subjects: List<Subject>
)