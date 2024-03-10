package ru.kondrashen.attendanciescoutapp.repository.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object APIFactory {
    private val gsonBuilder = GsonBuilder()
    private var retrofit = Retrofit.Builder()
        .baseUrl("https://attendanciescounts.serveo.net/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        .build()
    val groupsAPI = retrofit.create(GroupsAPI::class.java)
    val roleAPI = retrofit.create(RoleAPI::class.java)
    val userAPI = retrofit.create(UserAPI::class.java)
    val studentsAPI = retrofit.create(StudentsAPI::class.java)
    val subjectsAPI = retrofit.create(SubjectsAPI::class.java)
    val attendancesAPI = retrofit.create(AttendancesAPI::class.java)
}