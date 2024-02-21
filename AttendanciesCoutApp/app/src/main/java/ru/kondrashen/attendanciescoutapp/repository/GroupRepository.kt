package ru.kondrashen.attendanciescoutapp.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.api.APIFactory
import ru.kondrashen.attendanciescoutapp.repository.dao.GroupDAO
import kotlin.coroutines.CoroutineContext

class GroupRepository(private val groupDAO: GroupDAO): CoroutineScope {

    private val groupsAPI = APIFactory.groupsAPI
    private val groupO: LiveData<List<Group>> = groupDAO.getGroupsO()
    private val groupZ: LiveData<List<Group>> = groupDAO.getGroupsZ()
    private val groupOZ: LiveData<List<Group>> = groupDAO.getGroupsOZ()

    fun getGroupsOData(token: String?): LiveData<List<Group>> {
        getDataFromServer(token)
        return groupO
    }
    fun getGroupsZData(token: String?): LiveData<List<Group>> {
        getDataFromServer(token)
        return groupZ
    }


    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job
    private fun getDataFromServer(token: String?) {
        launch(Dispatchers.IO) {
            val resultToken = "Bearer $token"
            val resp = groupsAPI.getGroupsAuth(resultToken, 1) as MutableList<Group>
            println(resp)
            for (i in resp)
                groupDAO.addGroups(i)
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


}
