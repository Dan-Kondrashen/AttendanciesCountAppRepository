package ru.kondrashen.attendanciescoutapp.repository.data_class

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "group_table")
data class Group(
    @PrimaryKey
    var id: Int,
    var name: String,
    var year: String,
    var type: Int
)

