package ru.kondrashen.attendanciescoutapp.repository

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.api.APIFactory
import ru.kondrashen.attendanciescoutapp.repository.dao.GroupDAO
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddAttendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.Subject
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.SubjectToGroupCrossRef
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.AddAttendancesResponse
import kotlin.coroutines.CoroutineContext

class GroupRepository(private val groupDAO: GroupDAO): CoroutineScope {

    private val groupsAPI = APIFactory.groupsAPI
    private val studentsAPI = APIFactory.studentsAPI
    private val subjectsAPI = APIFactory.subjectsAPI
    private val attendancesAPI = APIFactory.attendancesAPI
    private lateinit var response: AddAttendancesResponse
    private val groupO: LiveData<List<Group>> = groupDAO.getGroupsO()
    private val groupZ: LiveData<List<Group>> = groupDAO.getGroupsZ()
    private val students: LiveData<List<Student>> = groupDAO.getAllStudents()
    private val subjects: LiveData<List<Subject>> = groupDAO.getAllSubjects()

    fun getGroupsOData(token: String?, id: Int): LiveData<List<Group>> {
        getDataFromServer(token, id)
        return groupO
    }
    fun getGroupsZData(token: String?, id: Int): LiveData<List<Group>> {
        getDataFromServer(token, id)
        return groupZ
    }

    fun getAllStudentsData(token: String?, id: Int): LiveData<List<Student>> {
        getDataFromServer(token, id)
        return students
    }

    fun getAllSubjectsData(token: String?, id: Int): LiveData<List<Subject>> {
        getDataFromServer(token, id)
        return subjects
    }

    fun getSubjectsByGroupId(grId: Int): LiveData<List<GroupWithSubjects>>{
        return groupDAO.getSubjectsOfGroup(grId)
    }

    fun getStudentsByGroupId(grId: Int): LiveData<List<Student>>{
        return groupDAO.getStudentsOfGroup(grId)
    }
    fun postAttendanceToServ( token: String?, attendances: List<AddAttendances>, id: Int): AddAttendancesResponse{
        val resultToken = "Bearer $token"
        val gson = Gson()
        val jsonMas = JsonObject()
        jsonMas.add("attendancies",gson.toJsonTree(attendances))
        println("Вот json")
        println(jsonMas)
        runBlocking {
            response = attendancesAPI.postAttendancesAsync(resultToken, jsonMas, id)
            println(response)
        }
        return response
    }

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job
    private fun getDataFromServer(token: String?, id: Int) {
        val resultToken = "Bearer $token"
        launch(Dispatchers.IO) {

            val resp = groupsAPI.getGroupsAuth(resultToken, id) as MutableList<Group>
            val resp2 = studentsAPI.getStudentsAuth(resultToken, id) as MutableList<Student>
            val resp3 = subjectsAPI.getSubjectsAuth(resultToken, id) as MutableList<Subject>
            val resp4 = groupsAPI.getSubToGrAuth(resultToken, id) as MutableList<SubjectToGroupCrossRef>
            println(resp)
            for (i in resp)
                groupDAO.addGroups(i)
            for (i in resp2)
                groupDAO.addStudent(i)
            for (i in resp3)
                groupDAO.addSubject(i)
            for (i in resp4)
                groupDAO.addSubjectToGr(i)
        }
    }
    }
//    private fun getDataFromServer(token: String?) {
//        launch(Dispatchers.IO) {
//            val resp = groupsAPI.getGroupsAsync() as MutableList<Group>
//            println(resp)
//            for (i in resp)
//                groupDAO.addGroups(i)
//        }
//    }



