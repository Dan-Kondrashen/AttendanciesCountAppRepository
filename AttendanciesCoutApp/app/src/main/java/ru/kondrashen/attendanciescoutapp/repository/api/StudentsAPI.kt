package ru.kondrashen.attendanciescoutapp.repository.api

import okhttp3.Call
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import ru.kondrashen.attendanciescoutapp.repository.data_class.AddStudFile
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.responces.AddAttendancesResponse

interface StudentsAPI {
    @GET("users/{user_id}/students")
    suspend fun getStudentsAuth(@Header("token")token: String, @Path("user_id") uid: Int): List<Student>
    @GET("students")
    suspend fun getAllStudentsAuth(@Header("token")token: String): List<Student>
//    @POST("students/{stud_id}")
//    suspend fun postStudentFileAuth(@Header("token")token: String, @Path("stud_id") sid: Int, @Body studFile: AddStudFile): AddAttendancesResponse
    @Multipart
    @POST("students/{stud_id}")
    suspend fun postStudentFileAuth(@Header("token")token: String, @Path("stud_id") sid: Int, @Part("name") name: RequestBody, @Part("startDate") date1: RequestBody, @Part("endDate") date2: RequestBody,  @Part part: MultipartBody.Part): AddAttendancesResponse
}