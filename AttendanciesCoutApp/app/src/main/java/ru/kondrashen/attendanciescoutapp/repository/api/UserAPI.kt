package ru.kondrashen.attendanciescoutapp.repository.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.kondrashen.attendanciescoutapp.repository.data_class.LoginResponse
import ru.kondrashen.attendanciescoutapp.repository.data_class.User
import ru.kondrashen.attendanciescoutapp.repository.data_class.UserLog

interface UserAPI {
    @GET("users")
    suspend fun getUsersAuth(): List<User>
    @GET("users/{user_id}")
    suspend fun getUserAsync(@Path("user_id") id: Int): User
    @POST("users/login")
    suspend fun postLoginDataAsync(@Body userLog: UserLog): LoginResponse
}