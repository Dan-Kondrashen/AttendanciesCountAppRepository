package ru.kondrashen.attendanciescoutapp.repository.api

import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.AddAttendancesResponse


interface AttendancesAPI {
//    @Headers("Content-Type: applicaction/json")
    @POST("users/{user_id}/attendancies")
    suspend fun postAttendancesAsync(@Header("Authorization") token: String?, @Body attendances: JsonObject, @Path("user_id") id: Int): AddAttendancesResponse
}