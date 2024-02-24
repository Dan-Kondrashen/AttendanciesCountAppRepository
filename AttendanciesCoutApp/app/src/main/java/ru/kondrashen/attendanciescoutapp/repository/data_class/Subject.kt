package ru.kondrashen.attendanciescoutapp.repository.data_class

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("subject_table")
data class Subject (
    @PrimaryKey
    var subjectId: Int,
    var name: String
)