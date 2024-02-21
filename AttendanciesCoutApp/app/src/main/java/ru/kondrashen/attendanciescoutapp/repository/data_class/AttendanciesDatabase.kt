package ru.kondrashen.attendanciescoutapp.repository.data_class

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.kondrashen.attendanciescoutapp.repository.converters.DateConverter
import ru.kondrashen.attendanciescoutapp.repository.dao.GroupDAO
import ru.kondrashen.attendanciescoutapp.repository.dao.RoleDAO

@Database(entities = [Group::class, Role::class, User::class, Student::class, Attendances::class, Subject::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AttendanciesDatabase: RoomDatabase() {
    abstract fun groupDao(): GroupDAO
    abstract fun roleDao(): RoleDAO

    companion object {
        @Volatile
        private var INSTANCE: AttendanciesDatabase? = null

        fun getDatabase(context: Context): AttendanciesDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AttendanciesDatabase::class.java,
                    "attendances_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}