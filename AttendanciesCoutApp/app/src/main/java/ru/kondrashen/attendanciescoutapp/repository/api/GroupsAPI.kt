package ru.kondrashen.attendanciescoutapp.repository.api
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.POST
import ru.kondrashen.attendanciescoutapp.repository.data_class.Group


interface GroupsAPI {
    @GET("groups")
    suspend fun getGroupsAsync(): List<Group>
    @GET("groups/{group_id}")
    suspend fun getGroupAsync(@Path("group_id") id: Int): Group

}
