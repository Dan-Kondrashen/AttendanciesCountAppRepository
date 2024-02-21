package ru.kondrashen.attendanciescoutapp.Domain


import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.kondrashen.attendanciescoutapp.repository.GroupRepository
import ru.kondrashen.attendanciescoutapp.repository.data_class.AttendanciesDatabase
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group

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
    val pref = application.getSharedPreferences("AuthPref", Context.MODE_PRIVATE)


    init {
        val groupDAO = AttendanciesDatabase.getDatabase(application).groupDao()
        grRepository = GroupRepository(groupDAO)
        groupO = groupDAO.getGroupsO()
        groupZ = groupDAO.getGroupsZ()
        groupOZ = groupDAO.getGroupsOZ()
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

    fun getGroupsOServ(): LiveData<List<Group>>{
        val token = pref.getString("token", null)
        groupO = grRepository.getGroupsOData(token)
        return groupO
    }

    fun getGroupsZServ(): LiveData<List<Group>>{
        val token = pref.getString("token", null)
        groupZ = grRepository.getGroupsZData(token)
        return groupZ
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

