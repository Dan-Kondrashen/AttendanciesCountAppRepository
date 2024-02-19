package ru.kondrashen.attendanciescoutapp.repository.data_class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student (
    @PrimaryKey
    var id: Int,
    var FIO: String,
    var groupId: Int,

)