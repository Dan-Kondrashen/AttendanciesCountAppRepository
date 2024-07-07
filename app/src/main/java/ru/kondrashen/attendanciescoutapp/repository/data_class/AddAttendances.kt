package ru.kondrashen.attendanciescoutapp.repository.data_class

import com.google.gson.annotations.SerializedName


data class AddAttendances(
    var subjectId: Int,
    var studentId: Int,
    var userId: Int,
    @SerializedName("statys")
    var status: String,
    var datetime: String
)
