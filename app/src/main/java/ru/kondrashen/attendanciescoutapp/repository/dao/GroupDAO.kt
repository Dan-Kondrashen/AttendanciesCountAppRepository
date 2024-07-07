package ru.kondrashen.attendanciescoutapp.repository.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.kondrashen.attendanciescoutapp.repository.data_class.Attendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendanceCount
import ru.kondrashen.attendanciescoutapp.repository.data_class.Subject
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithStudents
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.StudentInGroup
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.StudentsWithAttendancies
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.SubjectToGroupCrossRef

//import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
//import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithStudents

@Dao
interface GroupDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addGroups(group: Group)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addStudent(student: Student)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubject(subject: Subject)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSubjectToGr(subjectGr: SubjectToGroupCrossRef)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAttendance(attendances: Attendances)

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

    @Query("SELECT * FROM student_table WHERE groupId = :id")
    fun getStudentsOfGroup(id: Int): LiveData<List<Student>>

    @Query("SELECT * FROM student_table WHERE groupId = :id")
    fun getStudentsOfGroupStatic(id: Int): List<Student>

    @Query("SELECT * FROM group_table WHERE id = :grId")
    fun getSubjectsOfGroup(grId: Int): LiveData<List<GroupWithSubjects>>

    @Query("SELECT * FROM student_table ORDER BY FIO DESC")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM subject_table ORDER BY name DESC")
    fun getAllSubjects(): LiveData<List<Subject>>

    @Query("SELECT subjectId FROM subject_table WHERE name = :name")
    fun getSubjectId(name: String): LiveData<Int>

    @Query("SELECT * FROM attendance_table ORDER BY statys DESC")
    fun getAllAttendances(): LiveData<List<Attendances>>

    @Query("SELECT * FROM student_table WHERE groupId = :grId")
    fun getAttendancesByGrId(grId: Int): LiveData<List<StudentsWithAttendancies>>

    @Query("SELECT  studentId as id, FIO, COUNT(CASE WHEN attendance_table.statys = 'Отсутствовал' THEN 1 ELSE NULL END) AS summ FROM student_table LEFT JOIN attendance_table ON student_table.id = attendance_table.studentId GROUP BY student_table.FIO ORDER BY summ DESC")
    fun getAttendancesOfStudents(): LiveData<List<StudentAttendanceCount>>

    @Query("SELECT COUNT(*) FROM attendance_table WHERE studentId = :studId AND subjectId = :subId AND statys = :status")
    fun getSumAttendancesWithParam(studId: Int, subId: Int, status: String): Int
    @Query("Select * From student_table left join group_table ON groupId = group_table.id Where student_table.id = :studId")
    fun getChosedStudent(studId: Int): LiveData<StudentInGroup>

}