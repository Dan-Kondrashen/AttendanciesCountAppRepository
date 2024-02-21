package ru.kondrashen.attendanciescoutapp.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.kondrashen.attendanciescoutapp.repository.api.APIFactory

import ru.kondrashen.attendanciescoutapp.repository.dao.RoleDAO
import ru.kondrashen.attendanciescoutapp.repository.data_class.LoginResponse
import ru.kondrashen.attendanciescoutapp.repository.data_class.Role
import ru.kondrashen.attendanciescoutapp.repository.data_class.UserLog
import kotlin.coroutines.CoroutineContext

class AuthRepository(private val roleDAO: RoleDAO): CoroutineScope {
    private val roleAPI = APIFactory.roleAPI
    private val userAPI = APIFactory.userAPI
    private lateinit var response: LoginResponse
    private val roles: LiveData<List<String>> = roleDAO.getRoleForDropDown()
    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = job
    private fun getDataFromServer() {
        launch(Dispatchers.IO) {
            val resp = roleAPI.getRolesAsync() as MutableList<Role>
            for (i in resp)
                roleDAO.addRole(i)
        }
    }
    fun getRoleId(name: String): LiveData<Int>{
        return roleDAO.getRoleId(name)
    }

    fun getAllRolesName(): LiveData<List<String>> {
        getDataFromServer()
        return roles
    }

    fun postLoginData(userLog: UserLog): LoginResponse{
        runBlocking {
            response = userAPI.postLoginDataAsync(userLog)
            println(response)
        }
        return response
    }
}