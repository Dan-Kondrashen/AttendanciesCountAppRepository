package ru.kondrashen.attendanciescoutapp.repository.data_class

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey
    var id: Int,
    var FIO: String,
    var roleId: Int,
    var email: String,
    var phone: String

)