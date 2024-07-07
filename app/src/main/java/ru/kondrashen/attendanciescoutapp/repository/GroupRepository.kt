package ru.kondrashen.attendanciescoutapp.repository

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.api.APIFactory
import ru.kondrashen.attendanciescoutapp.repository.dao.GroupDAO
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddAttendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Attendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendanceCount
import ru.kondrashen.attendanciescoutapp.repository.data_class.Subject
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.StudentInGroup
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.StudentsWithAttendancies
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.SubjectToGroupCrossRef
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.AddAttendancesResponse
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.DataResponse
import kotlin.coroutines.CoroutineContext

class GroupRepository(private val groupDAO: GroupDAO): CoroutineScope {

    private val groupsAPI = APIFactory.groupsAPI
    private val studentsAPI = APIFactory.studentsAPI
    private val subjectsAPI = APIFactory.subjectsAPI
    private val attendancesAPI = APIFactory.attendancesAPI
    private lateinit var response: AddAttendancesResponse
    private lateinit var sresponse: DataResponse
    private val groupO: LiveData<List<Group>> = groupDAO.getGroupsO()
    private val groupZ: LiveData<List<Group>> = groupDAO.getGroupsZ()
    private val students: LiveData<List<Student>> = groupDAO.getAllStudents()
    private var studentsStatic: List<Student> = mutableListOf()
    private val subjects: LiveData<List<Subject>> = groupDAO.getAllSubjects()
    private val attendances: LiveData<List<Attendances>> = groupDAO.getAllAttendances()
    private var studAttendances: LiveData<List<StudentAttendanceCount>> = groupDAO.getAttendancesOfStudents()

    fun getGroupsOData(token: String?, id: Int): LiveData<List<Group>> {
        getStartedDataFromServer(token, id)
        return groupO
    }
    fun getAdminData(token: String?): LiveData<List<StudentAttendanceCount>> {
        getStartedAdminDataFromServer(token)
        return studAttendances
    }
    fun getAllAttendancesData(token: String?, id: Int): LiveData<List<Attendances>> {
        getAttendInfoFromServer(token, id)
        return attendances
    }
    fun getAttendancesByGroupId(grId: Int): LiveData<List<StudentsWithAttendancies>>{
        return groupDAO.getAttendancesByGrId(grId)
    }
    fun getSubjectId(name: String): LiveData<Int>{
        return groupDAO.getSubjectId(name)
    }
    fun getSubjectsByGroupId(grId: Int): LiveData<List<GroupWithSubjects>>{
        return groupDAO.getSubjectsOfGroup(grId)
    }
    fun getStudentsByGroupId(grId: Int): LiveData<List<Student>>{
        return groupDAO.getStudentsOfGroup(grId)
    }
    fun getStudentsCountAttend(studId: Int, subId: Int, status: String): Int{
        var result = 0
        runBlocking(Dispatchers.IO){
            result =groupDAO.getSumAttendancesWithParam(studId, subId, status)
        }
        return result
    }
    fun getStudentsByGroupIdStatic(grId: Int): List<Student>{

        runBlocking(Dispatchers.IO){
            studentsStatic =groupDAO.getStudentsOfGroupStatic(grId)
        }
        return studentsStatic
    }
    fun getCurStudent(studId: Int): LiveData<StudentInGroup>{
        return groupDAO.getChosedStudent(studId)
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
    private fun getStartedDataFromServer(token: String?, id: Int) {
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
    private fun getStartedAdminDataFromServer(token: String?) {
        val resultToken = "Bearer $token"
        launch(Dispatchers.IO) {
            val resp1 = attendancesAPI.getAllAttendancesAsync(resultToken) as MutableList<Attendances>
            val resp2 = studentsAPI.getAllStudentsAuth(resultToken) as MutableList<Student>
            val resp3 = groupsAPI.getAllGroupsAuth(resultToken) as MutableList<Group>
            for (i in resp3)
                groupDAO.addGroups(i)
            for (i in resp2)
                groupDAO.addStudent(i)
            for (i in resp1)
                groupDAO.addAttendance(i)

        }
    }

    private fun getAttendInfoFromServer(token: String?, id: Int) {
        val resultToken = "Bearer $token"
        runBlocking {
            val resp5 = attendancesAPI.getAttendancesAsync(resultToken, id) as MutableList<Attendances>
            println("Вот")
            println(resp5)
            for (i in resp5)
                groupDAO.addAttendance(i)
        }
    }

    fun postStudFileToServ(token: String?, name: RequestBody, date1: RequestBody, date2: RequestBody, addStudFile: MultipartBody.Part, studId: Int): AddAttendancesResponse {
        val resultToken = "Bearer $token"
        runBlocking(Dispatchers.IO) {
            response = studentsAPI.postStudentFileAuth(resultToken, studId, name, date1, date2, addStudFile)
            println(response)
        }
        return  response
    }

    fun putStudToServ(token: String?, studId: Int, student: Student): DataResponse {
        val resultToken = "Bearer $token"
        runBlocking(Dispatchers.IO) {
            sresponse = studentsAPI.putStudentAuth(resultToken, studId, student)
            println(sresponse)
        }
        return sresponse
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