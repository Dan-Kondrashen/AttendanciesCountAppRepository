package ru.kondrashen.attendanciescoutapp.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import ru.kondrashen.attendanciescoutapp.repository.data_class.Role

@Dao
interface RoleDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRole(role: Role)

    @Query("SELECT name FROM role_table ORDER BY name DESC")
    fun getRoleForDropDown(): LiveData<List<String>>

    @Query("SELECT id FROM role_table WHERE name = :name")
    fun getRoleId(name: String): LiveData<Int>
}