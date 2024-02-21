package ru.kondrashen.attendanciescoutapp.repository.data_class

data class LoginResponse(
    val status: String,
    val id: Int,
    val accessToken: String,
    val refreshToken: String
)
