package ru.kondrashen.attendanciescoutapp.repository.data_class

import androidx.room.Entity

@Entity(tableName = "users_to_groups_table", primaryKeys = ["studId", "groupId"])
data class GroupsUsersRefs(
    var studId: Int,
    var groupId: Int,
)