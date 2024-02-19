package ru.kondrashen.attendanciescoutapp.repository.api

import retrofit2.http.GET
import ru.kondrashen.attendanciescoutapp.repository.data_class.Role

interface RoleAPI {
    @GET("roles")
    suspend fun getRolesAsync(): List<Role>
}