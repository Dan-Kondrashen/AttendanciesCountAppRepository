package ru.kondrashen.attendanciescoutapp.repository.api
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group
import ru.kondrashen.attendanciescoutapp.repository.data_class.Student
import ru.kondrashen.attendanciescoutapp.repository.data_class.relations.SubjectToGroupCrossRef


interface GroupsAPI {

    @GET("groups")
    suspend fun getGroupsAsync(): List<Group>
    @Headers("Content-Type: application/json")
    @GET("users/{user_id}/groups")
    suspend fun getGroupsAuth(@Header("Authorization") token: String, @Path("user_id") uid: Int): List<Group>
    @Headers("Content-Type: application/json")
    @GET("users/{user_id}/subToGr")
    suspend fun getSubToGrAuth(@Header("Authorization") token: String, @Path("user_id") uid: Int): List<SubjectToGroupCrossRef>
    @Headers("Content-Type: application/json")
    @GET("users/{user_id}/groups/{group_id}")
    suspend fun getGroupStudentsAuth(@Header("Authorization") token: String, @Path("group_id") gid: Int, @Path("user_id") uid: Int): List<Student>

    @GET("groups/{group_id}")
    suspend fun getGroupAsync(@Path("group_id") id: Int): Group

}
