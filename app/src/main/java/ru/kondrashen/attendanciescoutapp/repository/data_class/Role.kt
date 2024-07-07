package ru.kondrashen.attendanciescoutapp.repository.data_class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "role_table")
data class Role (
    @PrimaryKey
    var id: Int,
    var name: String,
    var desc: String
)