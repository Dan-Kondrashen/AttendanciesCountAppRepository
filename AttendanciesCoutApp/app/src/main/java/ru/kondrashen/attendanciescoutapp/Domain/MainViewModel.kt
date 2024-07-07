package ru.kondrashen.attendanciescoutapp.Domain


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.kondrashen.attendanciescoutapp.repository.GroupRepository
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddAttendances
import ru.kondrashen.attendanciescoutapp.repository.data_class.AttendanciesDatabase
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.LoginResponse
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.GroupWithSubjects
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.AddAttendancesResponse

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
    private val pref = application.getSharedPreferences("AuthPref", Context.MODE_PRIVATE)
    private val token = pref.getString("token", null)


    init {
        val groupDAO = AttendanciesDatabase.getDatabase(application).groupDao()
        grRepository = GroupRepository(groupDAO)
        groupO = groupDAO.getGroupsO()
        groupZ = groupDAO.getGroupsZ()
        groupOZ = groupDAO.getGroupsOZ()
        students = groupDAO.getAllStudents()

    }

    fun getGroupsO(): LiveData<List<Group>> {
//        println("Вот оно: ${pref.getString("token", null)}")
        return groupO
    }

    fun getGroupsZ(): LiveData<List<Group>> {
        return groupZ
    }
    fun getGroupsOZ(): LiveData<List<Group>> {
        return groupOZ
    }

    fun getGroupsOServ(id: Int): LiveData<List<Group>>{

        val token = pref.getString("token", null)
        groupO = grRepository.getGroupsOData(token, id)
        return groupO
    }

//    fun getGroupsZServ(id: Int): LiveData<List<Group>>{
//        val token = pref.getString("token", null)
//        groupZ = grRepository.getGroupsZData(token, id)
//        return groupZ
//    }
    fun postAttendance(attendances: List<AddAttendances>, userId: Int): AddAttendancesResponse {
        return grRepository.postAttendanceToServ(token, attendances, userId)
    }
    fun getStudentsInRoom(id: Int): LiveData<List<Student>>{
        return grRepository.getStudentsByGroupId(id)
    }
    fun getSubjectsInRoom(id: Int): LiveData<List<GroupWithSubjects>>{
        return grRepository.getSubjectsByGroupId(id)
    }

    fun getAllStudentsFromServer(id: Int): LiveData<List<Student>>{
        val token = pref.getString("token", null)
        return grRepository.getAllStudentsData(token, id)
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

