package ru.kondrashen.attendanciescoutapp.repository.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student

interface StudentsAPI {
    @GET("users/{user_id}/students")
    suspend fun getStudentsAuth(@Header("token")token: String, @Path("user_id") uid: Int): List<Student>
}