package ru.kondrashen.attendanciescoutapp.Domain


import android.app.Application
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

    init {
        val groupDAO = AttendanciesDatabase.getDatabase(application).groupDao()
        grRepository = GroupRepository(groupDAO)
        groupO = groupDAO.getGroupsO()
        groupZ = groupDAO.getGroupsZ()
        groupOZ = groupDAO.getGroupsOZ()
    }

    fun getGroupsO(): LiveData<List<Group>> {
        return groupO
    }

    fun getGroupsZ(): LiveData<List<Group>> {
        return groupZ
    }
    fun getGroupsOZ(): LiveData<List<Group>> {
        return groupOZ
    }

    fun getGroupsOServ(): LiveData<List<Group>>{
        groupO = grRepository.getGroupsOData()
        return groupO
    }

    fun getGroupsZServ(): LiveData<List<Group>>{
        groupZ = grRepository.getGroupsZData()
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

