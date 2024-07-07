package ru.kondrashen.attendanciescoutapp.repository.data_class.relations

import androidx.room.Entity

@Entity(primaryKeys = ["id","subjectId"])
data class SubjectToGroupCrossRef(
    val id: Int,
    val subjectId: Int
)
