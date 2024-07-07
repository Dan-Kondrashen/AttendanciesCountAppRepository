package ru.kondrashen.attendanciescoutapp.Domain


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import ru.kondrashen.attendanciescoutapp.repository.GroupRepository
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddAttendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.Attendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.AttendanciesDatabase
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.StudentAttendanceCount
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.StudentInGroup
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.StudentsWithAttendancies
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.AddAttendancesResponse
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.DataResponse

//open class MainViewModel: ViewModel() {
////    private val groupsAPI = APIFactory.groupsAPI
//    private val grRepository = GroupRepository()
//    private val group: MutableLiveData<MutableList<Group>> = grRepository.getGroupsData()
//
//    fun getGroups():MutableLiveData<MutableList<Group>> {
//        return group
//    }
//  }

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val grRepository: GroupRepository

    private var groupO: LiveData<List<Group>>
    private var groupZ: LiveData<List<Group>>
    private var groupOZ: LiveData<List<Group>>
    private var students: LiveData<List<Student>>
    private var attendances: LiveData<List<Attendances>>
    private var studAttendances: LiveData<List<StudentAttendanceCount>>
    private val pref = application.getSharedPreferences("AuthPref", Context.MODE_PRIVATE)
    private val token = pref.getString("token", null)


    init {
        val groupDAO = AttendanciesDatabase.getDatabase(application).groupDao()
        grRepository = GroupRepository(groupDAO)
        groupO = groupDAO.getGroupsO()
        groupZ = groupDAO.getGroupsZ()
        groupOZ = groupDAO.getGroupsOZ()
        students = groupDAO.getAllStudents()
        attendances = groupDAO.getAllAttendances()
        studAttendances = groupDAO.getAttendancesOfStudents()

    }

    fun getGroupsO(): LiveData<List<Group>> {
//        println("Вот оно: ${pref.getString("token", null)}")
        return groupO
    }

    fun getGroupsZ(): LiveData<List<Group>> {
        return groupZ
    }
    fun getAdminAttendance(): LiveData<List<StudentAttendanceCount>> {
        return studAttendances
    }
    fun getGroupsOZ(): LiveData<List<Group>> {
        return groupOZ
    }

    fun getSubjectsId(name: String): LiveData<Int> {
        return grRepository.getSubjectId(name)
    }

    fun getAdminStartDataServ(): LiveData<List<StudentAttendanceCount>>{
        val token = pref.getString("token", null)
        studAttendances = grRepository.getAdminData(token)
        return studAttendances
    }
    fun getGroupsOServ(id: Int): LiveData<List<Group>>{

        val token = pref.getString("token", null)
        groupO = grRepository.getGroupsOData(token, id)
        return groupO
    }
    fun getAttendGroupInfoFromServer(id: Int): LiveData<List<Attendances>>{
        val token = pref.getString("token", null)
        attendances = grRepository.getAllAttendancesData(token, id)
        return attendances
    }

//    fun getGroupsZServ(id: Int): LiveData<List<Group>>{
//        val token = pref.getString("token", null)
//        groupZ = grRepository.getGroupsZData(token, id)
//        return groupZ
//    }
    fun postAttendance(attendances: List<AddAttendances>, userId: Int): AddAttendancesResponse {
        return grRepository.postAttendanceToServ(token, attendances, userId)
    }
    fun postDocument(addStudFile: MultipartBody.Part,name: RequestBody, date1: RequestBody, date2: RequestBody, studId: Int): AddAttendancesResponse{
        return grRepository.postStudFileToServ(token, name, date1,date2, addStudFile, studId)
    }
    fun putStudent(student: Student): DataResponse{
        return grRepository.putStudToServ(token, student.id, student)
    }
    fun getStudentsInRoom(id: Int): LiveData<List<Student>>{
        return grRepository.getStudentsByGroupId(id)
    }
    fun getStudentsInRoomStatic(id: Int): List<Student>{
        return grRepository.getStudentsByGroupIdStatic(id)
    }
    fun getStudentInfo(studId: Int): LiveData<StudentInGroup>{
        return grRepository.getCurStudent(studId)
    }
    fun countStudentAttend(studId: Int, subId: Int, status: String): Int {
        return grRepository.getStudentsCountAttend(studId, subId, status)
    }
    fun getSubjectsInRoom(id: Int): LiveData<List<GroupWithSubjects>>{
        return grRepository.getSubjectsByGroupId(id)
    }
    fun getAttendancesInRoom(id: Int): LiveData<List<StudentsWithAttendancies>>{
        return grRepository.getAttendancesByGroupId(id)
    }
}
//    fun getData() {
//        if (group.value == null) {
//            viewModelScope.launch {
//                val resp = groupsAPI.getGroupsAsync() as MutableList<Group>
//                group.value = resp
//            }
//        }
//    }

