package ru.kondrashen.attendanciescoutapp.repository.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithStudents

//import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
//import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithStudents

@Dao
interface GroupDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGroups(group: Group)

    @Query("SELECT * FROM group_table ORDER BY name DESC")
    fun getGroups(): LiveData<List<Group>>

    @Query("SELECT * FROM group_table WHERE type = 1 ORDER BY name DESC")
    fun getGroupsO(): LiveData<List<Group>>

    @Query("SELECT * FROM group_table WHERE type = 2 ORDER BY name DESC")
    fun getGroupsZ(): LiveData<List<Group>>

    @Query("SELECT * FROM group_table WHERE type = 3 ORDER BY name DESC")
    fun getGroupsOZ(): LiveData<List<Group>>

    @Query("SELECT * FROM group_table WHERE id = :id")
    fun getStudentOfGroup(id: Int): LiveData<List<GroupWithStudents>>
}






