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

    fun getGroupsOData(): LiveData<List<Group>> {
        getDataFromServer()
        return groupO
    }
    fun getGroupsZData(): LiveData<List<Group>> {
        getDataFromServer()
        return groupZ
    }


    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job
    private fun getDataFromServer() {
        launch(Dispatchers.IO) {
            val resp = groupsAPI.getGroupsAsync() as MutableList<Group>
            println(resp)
            for (i in resp)
                groupDAO.addGroups(i)
            }
    }

}
