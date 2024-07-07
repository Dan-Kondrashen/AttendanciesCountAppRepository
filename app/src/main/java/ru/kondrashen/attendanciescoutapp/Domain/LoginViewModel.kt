package ru.kondrashen.attendanciescoutapp.Domain

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import ru.kondrashen.attendanciescoutapp.repository.AuthRepository
import ru.kondrashen.attendanciescoutapp.repository.data_class.AttendanciesDatabase
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.LoginResponse
import ru.kondrashen.attendanciescoutapp.repository.data_class.UserLog

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val roleRepository: AuthRepository
    private var role: LiveData<List<String>>
    val pref = application.getSharedPreferences("AuthPref", Context.MODE_PRIVATE)
    val editor = pref.edit()

    private lateinit var response: LoginResponse
    init {
        val roleDAO = AttendanciesDatabase.getDatabase(application).roleDao()
        roleRepository = AuthRepository(roleDAO)
        role = roleDAO.getRoleForDropDown()
    }

    fun getRolesFromServer(): LiveData<List<String>>{
        role = roleRepository.getAllRolesName()
        return role
    }

    fun getRolesFromRoom(): LiveData<List<String>>{
        return role
    }

    fun clearDatabase(){
        roleRepository.clearUserData()
    }

    fun getRolesId(name: String): LiveData<Int> {
        roleRepository.getRoleId(name)
        return roleRepository.getRoleId(name)
    }

    fun goToAccaunt(userlog: UserLog): LoginResponse {
        response = roleRepository.postLoginData(userlog)
        editor.putString("token", response.accessToken)
        editor.apply()
//        println("ВОТ ОНООО ${pref.getString("token",null)}")
        return  response
    }

}