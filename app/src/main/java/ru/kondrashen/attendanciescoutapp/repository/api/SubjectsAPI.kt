package ru.kondrashen.attendanciescoutapp.repository.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import ru.kondrashen.attendanciescoutapp.repository.data_class.Subject

interface SubjectsAPI {
    @GET("users/{user_id}/subjects")
    suspend fun getSubjectsAuth(@Header("token")token: String, @Path("user_id") uid: Int): List<Subject>
}