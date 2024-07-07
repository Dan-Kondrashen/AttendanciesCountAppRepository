package ru.kondrashen.attendanciescoutapp.repository.data_class.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Subject

data class SubjectWithGroups (
    @Embedded val subject: Subject,
    @Relation(parentColumn = "subjectId",
        entityColumn = "id",
        associateBy = Junction(SubjectToGroupCrossRef::class)
    )
    val groups: List<Group>
)