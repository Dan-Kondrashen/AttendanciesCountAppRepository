package ru.kondrashen.attendanciescoutapp.Domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import ru.kondrashen.attendanciescoutapp.repository.RoleRepository
import ru.kondrashen.attendanciescoutapp.repository.data_class.AttendanciesDatabase
import ru.kondrashen.attendanciescoutapp.repository.data_class.LoginResponse
import ru.kondrashen.attendanciescoutapp.repository.data_class.UserLog

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val roleRepository: RoleRepository
    private var role: LiveData<List<String>>

    private lateinit var response: LoginResponse
    init {
        val roleDAO = AttendanciesDatabase.getDatabase(application).roleDao()
        roleRepository = RoleRepository(roleDAO)
        role = roleDAO.getRoleForDropDown()

    }

    fun getRolesFromServer(): LiveData<List<String>>{
        role = roleRepository.getAllRolesName()
        return role
    }
    fun getRolesFromRoom(): LiveData<List<String>>{
        return role
    }
    fun getRolesId(name: String): LiveData<Int> {
        roleRepository.getRoleId(name)
        return roleRepository.getRoleId(name)
    }
    fun goToAccaunt(userlog: UserLog): LoginResponse{
        response = roleRepository.postLoginData(userlog)
        return  response
    }
}